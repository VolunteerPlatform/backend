package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VolActivityDayOfWeek {

    @Id
    @GeneratedValue
    @Column(name = "vol_activity_day_of_week_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek activityWeek;

    private Integer startTime;

    private Integer endTime;

    private Integer numOfRecruit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vol_activity_id")
    private VolActivity volActivity;
}
