package com.volunteer_platform.volunteer_platform.domain.member.form;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member1365Info;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.models.MembershipStatus;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Data
public class MemberForm {

    private String userName;
    private String password;
    private String kakaoId;
    private String userRealName;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String idOf1365;
    private String centerName;

    public Member toEntity() {
        return Member.builder()
                .userName(this.userName)
                .password(this.getPassword())
                .kakaoId("")
                .roles(Collections.singletonList("ROLE_USER"))
                .membershipStatus(MembershipStatus.REGISTERED)
                .build();
    }

    public MemberInfo toMemberInfoEntity(Member member) {
        return MemberInfo.builder()
                .member(member)
                .userRealName(this.getUserRealName())
                .birthday(this.getBirthday())
                .gender(this.getGender())
                .phoneNumber(this.getPhoneNumber())
                .build();
    }

    public Member1365Info toMember1365InfoEntity(Member member) {
        return Member1365Info.builder()
                .member(member)
                .idOf1365(this.getIdOf1365())
                .centerName(this.getCenterName())
                .build();
    }

    public void encoding(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
