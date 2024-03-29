package com.volunteer_platform.volunteer_platform.domain.member.models;

import com.volunteer_platform.volunteer_platform.domain.member.form.EditPasswordForm;
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
public class Member implements UserDetails {

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

    private String userName;
    private String password;
    private String kakaoId;

    @ElementCollection(fetch = EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MembershipStatus membershipStatus;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * MembershipStatus REGISTERED -> WITHDRAWAL 으로 update 로직
     */
    public void updateMembership() {
        this.membershipStatus = MembershipStatus.WITHDRAWAL;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
