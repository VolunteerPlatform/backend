package com.volunteer_platform.volunteer_platform.domain.member.repository;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    @Query("select m from Member m where m.userName = :userName")
    Optional<Member> findByUserName(@Param("userName") String username);

    @Query("select m from Member as m where m.id= :memberId")
    Optional<Member> findMemberId(@Param("memberId") Long memberId);

    @Query("select m from Member m where m.userName = :userName")
    Member getMembersByMemberId(@Param("userName") String userName);

    @Query("select m from Member m where m.userName = :userName")
    Optional<Member> getMemberId(@Param("userName") String userName);

    @Modifying
    @Transactional
    @Query("update Member as m set m.password = :pwd where m.userName = :userName")
    void updateMemberPwd(@Param("pwd") String newPwd, @Param("userName") String username);

    @Query("select m.userName from Member m where m.memberInfo= :memberInfoId")
    String findUserName(@Param("memberInfoId") MemberInfo memberInfo);
}
