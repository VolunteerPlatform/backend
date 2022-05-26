package com.volunteer_platform.volunteer_platform.domain.member.models;

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

    private String userRealName;
    private String birthday;
    private String gender;
    private String phoneNumber;

    public MemberInfo(String userRealName, String birthday, String gender, String phoneNumber) {
        this.userRealName = userRealName;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }
}
