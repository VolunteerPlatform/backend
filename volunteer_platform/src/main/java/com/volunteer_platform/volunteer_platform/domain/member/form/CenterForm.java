package com.volunteer_platform.volunteer_platform.domain.member.form;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MembershipStatus;
import lombok.Data;

@Data
public class CenterForm {

    private String userName;
    private String password;
    private String kakaoId;
    private MembershipStatus membershipStatus;

    public Member toEntity() {
        return Member.builder()
                .userName(this.userName)
                .password(this.getPassword())
                .kakaoId("ADMIN")
                .membershipStatus(MembershipStatus.ADMIN)
                .build();
    }
}
