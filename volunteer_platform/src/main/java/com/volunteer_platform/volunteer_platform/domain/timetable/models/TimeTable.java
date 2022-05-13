package com.volunteer_platform.volunteer_platform.domain.timetable.models;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Week;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class TimeTable {

    @Id @GeneratedValue
    @Column(name = "time_table_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Week week;

    private int startTime;
    private int endTime;
}
