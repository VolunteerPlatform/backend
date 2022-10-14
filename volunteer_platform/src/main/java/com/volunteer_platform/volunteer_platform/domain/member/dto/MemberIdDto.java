package com.volunteer_platform.volunteer_platform.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberIdDto {

    private Long memberId;

    public MemberIdDto(Long memberId) {
        this.memberId = memberId;
    }
}
