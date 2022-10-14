package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.models.QVolActivitySession.volActivitySession;

@Repository
@RequiredArgsConstructor
public class CustomActivitySessionRepository {

    private final JPAQueryFactory queryFactory;

    public List<VolActivitySession> findActivitySession(Long activityId, LocalDate activityDate) {
        return queryFactory
                .selectFrom(volActivitySession)
                .where(volActivitySession.volActivity.id.eq(activityId), activityDateEq(activityDate))
                .fetch();
    }

    private BooleanExpression activityDateEq(LocalDate activityDate) {
        return activityDate == null ? null : volActivitySession.activityDate.eq(activityDate);
    }
}
