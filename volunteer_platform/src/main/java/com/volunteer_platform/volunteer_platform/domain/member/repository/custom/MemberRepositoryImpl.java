package com.volunteer_platform.volunteer_platform.domain.member.repository.custom;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;
import com.volunteer_platform.volunteer_platform.domain.member.models.QMember;
import com.volunteer_platform.volunteer_platform.domain.member.models.QMember1365Info;
import com.volunteer_platform.volunteer_platform.domain.member.models.QMemberInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public MemberDto getMemberProfile(Long memberId) {
        QMember member = QMember.member;
        QMemberInfo memberInfo = QMemberInfo.memberInfo;
        QMember1365Info member1365Info = QMember1365Info.member1365Info;

        return queryFactory
                .select(Projections.constructor(MemberDto.class,
                        member.id,
                        member.userName,
                        member.kakaoId,
                        memberInfo.userRealName,
                        memberInfo.birthday,
                        memberInfo.gender,
                        memberInfo.phoneNumber,
                        member1365Info.idOf1365,
                        member1365Info.centerName
                ))
                .from(member)
                .join(memberInfo).on(member.id.eq(memberInfo.member.id)).fetchJoin()
                .join(member1365Info).on(member.id.eq(member1365Info.member.id)).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();
    }
}
