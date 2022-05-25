package com.volunteer_platform.volunteer_platform.domain.member.repository;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserName(String username);

    Optional<Member> findByEmail(String email);

    @Query("select m from Member as m where m.id= :id")
    Member findMemberId(Long id);

}
