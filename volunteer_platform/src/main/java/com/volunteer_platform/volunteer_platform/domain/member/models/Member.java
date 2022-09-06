package com.volunteer_platform.volunteer_platform.domain.member.models;

import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.*;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @OneToOne(mappedBy = "member", fetch = LAZY)
    private MemberInfo memberInfo;

    @OneToOne(mappedBy = "member", fetch = LAZY)
    private Member1365Info member1365Info;

    @OneToMany(mappedBy = "member")
    private List<TimeTable> timeTables = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<AppHistory> appHistories = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = LAZY)
    private Membership membership;

    private String loginId;
    private String password;
    private String kakaoId;


    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;


    /**
     * MembershipStatus REGISTERED -> WITHDRAWAL 으로 update 로직
     */
    public void updateMembership() {
        this.membershipStatus = MembershipStatus.WITHDRAWAL;
    }
}
