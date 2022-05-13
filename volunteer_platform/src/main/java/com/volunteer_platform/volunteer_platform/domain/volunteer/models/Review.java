package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vol_activity_id")
    private VolActivity volActivity;

    private int rating;
    private String comment;
}
