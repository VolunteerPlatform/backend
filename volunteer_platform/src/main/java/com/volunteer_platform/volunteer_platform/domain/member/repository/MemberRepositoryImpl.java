package com.volunteer_platform.volunteer_platform.domain.member.repository;

import com.volunteer_platform.volunteer_platform.domain.member.controller.dto.MemberDto;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final EntityManager em;

    @Override
    public MemberDto findMemberProfileCustom(Long id) {

        return em.createQuery(
                "select new com.volunteer_platform.volunteer_platform.domain.member.controller.dto.MemberDto(m.loginId, m.kakaoId, mi.birthday, mi.gender, mi.phoneNumber, mi.memberName, m1.centerName, m1.idOf1365)" +
                        " from Member m" +
                        " join m.memberInfo mi" +
                        " join m.member1365Info m1" +
                        " where m.id = :id", MemberDto.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
