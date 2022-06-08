package com.volunteer_platform.volunteer_platform.domain.member.dto;

import lombok.Data;

@Data
public class MemberPwdUpdateDto {

    String originPwd;
    String newPwd;
}
