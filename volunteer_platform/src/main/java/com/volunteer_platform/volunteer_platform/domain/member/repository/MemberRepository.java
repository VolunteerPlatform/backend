package com.volunteer_platform.volunteer_platform.domain.member.repository;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    @Query("select m from Member as m where m.loginId= :loginId")
    Optional<Member> findByMemberId(@Param("loginId") String loginId);

    @Query("select m from Member as m where m.id= :id")
    Optional<Member> findMemberId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Member as m set m.password = :pwd where m.loginId = :loginId")
    void updateMemberPwd(@Param("pwd") String newPwd, @Param("loginId") String loginId);

    @Query("select m.loginId from Member m where m.memberInfo= :memberInfoId")
    String findUserName(@Param("memberInfoId") MemberInfo memberInfo);

}
