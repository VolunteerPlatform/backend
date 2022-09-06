package com.volunteer_platform.volunteer_platform.domain.member.controller.dto;

import lombok.Data;

@Data
public class MemberProfileUpdateDto {

    private String memberName;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String idOf1365;
    private String centerName;
}
