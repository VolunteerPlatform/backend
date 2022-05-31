package com.volunteer_platform.volunteer_platform.domain.member.repository;

import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;

public interface MemberRepositoryCustom {
    MemberDto findMemberProfileCustom(Long id);
}
