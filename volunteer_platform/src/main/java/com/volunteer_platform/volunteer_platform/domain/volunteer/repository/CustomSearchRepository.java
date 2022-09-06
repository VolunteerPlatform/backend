package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.SearchCondition;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.QSearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Coordinate;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Category;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.models.QVolActivity.volActivity;
import static com.volunteer_platform.volunteer_platform.domain.volunteer.models.QVolActivitySession.volActivitySession;
import static com.volunteer_platform.volunteer_platform.domain.volunteer.models.QVolOrgan.volOrgan;

@Repository
public class CustomSearchRepository {

    private final JPAQueryFactory queryFactory;
    private final JdbcTemplate jdbcTemplate;

    public CustomSearchRepository(JPAQueryFactory queryFactory, DataSource dataSource) {
        this.queryFactory = queryFactory;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final int SEARCH_METER_RANGE = 3000;
    private static final String FIND_NEARBY_ORGAN_QUERY = "select vol_organ_id from vol_organ vo where ST_DISTANCE_SPHERE(Point(?, ?), Point(vo.longitude, vo.latitude)) <= ?";

    public List<SearchResultDto> searchActivity(SearchCondition searchCondition) {
        List<Long> nearbyOrgan = searchNearbyOrganization(searchCondition.getLongitude(), searchCondition.getLatitude());
        if (nearbyOrgan.isEmpty()) {
            return new ArrayList<>();
        }

        return sortByDistanceAsc(searchByConditionAndOrgan(searchCondition, nearbyOrgan), searchCondition);
    }

    private List<Long> searchNearbyOrganization(double longitude, double latitude) {
        return jdbcTemplate.queryForList(FIND_NEARBY_ORGAN_QUERY,
                Long.class,
                longitude,
                latitude,
                SEARCH_METER_RANGE);
    }

    private List<SearchResultDto> searchByConditionAndOrgan(SearchCondition searchCondition, List<Long> nearbyOrgan) {
        return queryFactory
                .select(new QSearchResultDto(
                        volActivity.id,
                        volActivitySession.id,
                        volOrgan.id,
                        volActivity.activityName,
                        volOrgan.name,
                        volActivitySession.activityDate,
                        volActivitySession.startTime,
                        volActivitySession.endTime,
                        volActivity.category,
                        volActivity.activityMethod,
                        volOrgan.address.coordinate.longitude,
                        volOrgan.address.coordinate.latitude
                ))
                .from(volActivitySession)
                .join(volActivitySession.volActivity, volActivity)
                .join(volActivity.volOrgan, volOrgan)
                .where(
                        isRecruiting(),
                        categoryEq(searchCondition.getCategory()),
                        activityDateGoe(searchCondition.getStartDate()),
                        activityDateLoe(searchCondition.getEndDate()),
                        organIdIn(nearbyOrgan))
                .fetch();
    }

    private List<SearchResultDto> sortByDistanceAsc(List<SearchResultDto> sessionList, SearchCondition searchCondition) {
        Coordinate userLocation = new Coordinate(searchCondition.getLongitude(), searchCondition.getLatitude());

        sessionList.sort((firstSession, secondSession) -> {
            Double firstSessionDistance = distanceBetweenCoords(
                    userLocation,
                    new Coordinate(firstSession.getLongitude(), firstSession.getLatitude()));
            Double secondSessionDistance = distanceBetweenCoords(
                    userLocation,
                    new Coordinate(secondSession.getLongitude(), secondSession.getLatitude()));

            return Double.compare(firstSessionDistance, secondSessionDistance);
        });

        return sessionList;
    }

    private BooleanExpression activityDateGoe(LocalDate startDate) {
        return startDate == null ? null : volActivitySession.activityDate.goe(startDate);
    }

    private BooleanExpression activityDateLoe(LocalDate endDate) {
        return endDate == null ? null : volActivitySession.activityDate.loe(endDate);
    }

    private BooleanExpression categoryEq(Category category) {
        return category == null ? null : volActivity.category.eq(category);
    }

    private BooleanExpression organIdIn(List<Long> nearbyOrgan) {
        return volActivity.volOrgan.id.in(nearbyOrgan);
    }

    private BooleanExpression isRecruiting() {
        return volActivitySession.sessionStatus.eq(SessionStatus.RECRUITING);
    }

    public Double distanceBetweenCoords(Coordinate first, Coordinate second) {
        double dx = first.getLongitude() - second.getLongitude();
        double dy = first.getLatitude() - second.getLatitude();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
