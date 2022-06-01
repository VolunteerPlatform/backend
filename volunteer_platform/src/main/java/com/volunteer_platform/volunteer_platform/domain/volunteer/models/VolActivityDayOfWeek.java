package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VolActivityDayOfWeek {

    @Id
    @GeneratedValue
    @Column(name = "vol_activity_day_of_week_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek activityWeek;

    private Integer startTime;

    private Integer endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vol_activity_id")
    private VolActivity volActivity;
}
