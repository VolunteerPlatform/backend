package com.volunteer_platform.volunteer_platform.domain.member.models;

import com.volunteer_platform.volunteer_platform.domain.member.controller.dto.MemberProfileUpdateDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberInfo {

    @Id @GeneratedValue
    @Column(name = "member_info_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String memberName;
    private String birthday;
    private String gender;
    private String phoneNumber;

    /**
     * MemberInfo 수정을 위한 비즈니스 로직
     * @param memberProfileUpdateDto
     */
    public void updateMemberInfo(MemberProfileUpdateDto memberProfileUpdateDto) {
        this.memberName = memberProfileUpdateDto.getMemberName();
        this.birthday = memberProfileUpdateDto.getBirthday();
        this.gender = memberProfileUpdateDto.getGender();
        this.phoneNumber = memberProfileUpdateDto.getPhoneNumber();
    }
}
