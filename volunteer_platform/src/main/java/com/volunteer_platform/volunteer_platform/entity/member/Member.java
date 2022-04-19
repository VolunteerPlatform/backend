package com.volunteer_platform.volunteer_platform.entity.member;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

    private String e_mail;
    private String password;
    private String kakao_id;
    private String google_id;

}
