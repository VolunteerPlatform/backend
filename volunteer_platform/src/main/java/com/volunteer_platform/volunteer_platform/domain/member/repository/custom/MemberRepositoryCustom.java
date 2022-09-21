package com.volunteer_platform.volunteer_platform.domain.member.repository.custom;

import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;

public interface MemberRepositoryCustom {

    MemberDto getMemberProfile(Long memberId);
}
