package com.volunteer_platform.volunteer_platform.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {

    private String userName;
    private String kakaoId;
    private String userRealName;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String idOf1365;
    private String centerName;
}
