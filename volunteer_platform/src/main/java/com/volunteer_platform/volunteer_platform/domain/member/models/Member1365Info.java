package com.volunteer_platform.volunteer_platform.domain.member.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "member_1365_info")
public class Member1365Info {

    @Id @GeneratedValue
    @Column(name = "member_1365_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String idOf1365;
    private String centerName;

    public Member1365Info(String idOf1365, String centerName) {
        this.idOf1365 = idOf1365;
        this.centerName = centerName;
    }
}
