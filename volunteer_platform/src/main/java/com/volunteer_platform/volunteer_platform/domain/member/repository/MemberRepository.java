package com.volunteer_platform.volunteer_platform.domain.member.repository;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
