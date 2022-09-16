package com.volunteer_platform.volunteer_platform.domain.member.controller.form;

import com.volunteer_platform.volunteer_platform.domain.member.models.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberForm {

    private String loginId;
    private String password;
    private String kakaoId;
    private MembershipStatus membershipStatus;
    private MemberRole memberRole;
    private Member member;
    private String memberName;
    private String birthday;
    private String gender;
    private String phoneNumber;
    private String idOf1365;
    private String centerName;

    public Member toEntity(Member member) {
        return Member.builder()
                .loginId(this.getLoginId())
                .password(this.getPassword())
                .kakaoId("")
                .membershipStatus(MembershipStatus.REGISTERED)
                .memberRole(MemberRole.ROLE_MEMBER)
                .build();
    }

    public MemberInfo toEntity(MemberInfo memberInfo, Member member) {
        return MemberInfo.builder()
                .member(member)
                .memberName(this.getMemberName())
                .birthday(this.getBirthday())
                .gender(this.getGender())
                .phoneNumber(this.getPhoneNumber())
                .build();
    }

    public Member1365Info toEntity(Member1365Info member1365Info, Member member) {
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
