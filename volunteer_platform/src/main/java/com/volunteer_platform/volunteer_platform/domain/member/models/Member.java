package com.volunteer_platform.volunteer_platform.domain.member.models;

import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Review;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberInfo memberInfo;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Member1365Info member1365Info;

    @OneToMany(mappedBy = "member")
    private List<TimeTable> timeTables = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<AppHistory> appHistories = new ArrayList<>();

    private String email;
    private String password;
    private String kakaoId;
    private String googleId;

    public Member(String email) {
        this.email = email;
    }
}
