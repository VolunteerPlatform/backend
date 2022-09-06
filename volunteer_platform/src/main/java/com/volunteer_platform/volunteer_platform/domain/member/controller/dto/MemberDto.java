package com.volunteer_platform.volunteer_platform.domain.member.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {

    private String loginId;
    private String kakaoId;
    private String memberName;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String idOf1365;
    private String centerName;
}
