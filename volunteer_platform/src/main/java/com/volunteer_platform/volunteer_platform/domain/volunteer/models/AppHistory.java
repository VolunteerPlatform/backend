package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.PrivacyApproval;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AppHistory {

    @Id @GeneratedValue
    @Column(name = "app_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String comment;

    @Enumerated(EnumType.STRING)
    private PrivacyApproval privacyApproval;

    @Enumerated(EnumType.STRING)
    private IsAuthorized isAuthorized;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vol_activity_time_id")
    private VolActivityTime volActivityTime;

    public void approve() {
        isAuthorized = IsAuthorized.APPROVAL;
    }

    public void deny() {
        isAuthorized = IsAuthorized.DISAPPROVAL;
    }

    public void pend() {
        isAuthorized = IsAuthorized.WAITING;
    }

    public void finish() {
        isAuthorized = IsAuthorized.COMPLETE;
    }
}
