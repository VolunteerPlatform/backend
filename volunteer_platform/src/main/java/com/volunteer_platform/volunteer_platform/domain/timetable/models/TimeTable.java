package com.volunteer_platform.volunteer_platform.domain.timetable.models;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;

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
    private DayOfWeek dayOfWeek;

    private int startTime;
    private int endTime;
}
