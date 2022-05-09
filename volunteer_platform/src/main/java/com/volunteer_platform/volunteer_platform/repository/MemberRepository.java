package com.volunteer_platform.volunteer_platform.repository;

import com.volunteer_platform.volunteer_platform.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
