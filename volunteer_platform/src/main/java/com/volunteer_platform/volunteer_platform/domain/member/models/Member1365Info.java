package com.volunteer_platform.volunteer_platform.domain.member.models;

import com.volunteer_platform.volunteer_platform.domain.member.controller.dto.MemberProfileUpdateDto;
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

    /**
     * Member1365Info 수정을 위한 비즈니스 로
     * @param memberProfileUpdateDto
     */
    public void updateMember1365Info(MemberProfileUpdateDto memberProfileUpdateDto) {
        this.idOf1365 = memberProfileUpdateDto.getIdOf1365();
        this.centerName = memberProfileUpdateDto.getCenterName();
    }
}
