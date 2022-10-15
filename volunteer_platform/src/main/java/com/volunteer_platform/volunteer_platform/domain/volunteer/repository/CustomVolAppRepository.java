package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.models.QAppHistory.appHistory;
import static com.volunteer_platform.volunteer_platform.domain.volunteer.models.QVolActivitySession.volActivitySession;

@Repository
@RequiredArgsConstructor
public class CustomVolAppRepository {

    private final JPAQueryFactory queryFactory;

    public List<AppHistory> findApplicantsByCondition(Long activityTimeId, IsAuthorized isAuthorized, Pageable pageable) {
        return queryFactory.selectFrom(appHistory)
                .join(appHistory.volActivitySession, volActivitySession).fetchJoin()
                .where(isAuthorizedEq(isAuthorized),
                        appHistory.volActivitySession.id.eq(activityTimeId),
                        appHistory.isAuthorized.ne(IsAuthorized.CANCELED))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression isAuthorizedEq(IsAuthorized isAuthorized) {
        return isAuthorized == null ? null : appHistory.isAuthorized.eq(isAuthorized);
    }
}
