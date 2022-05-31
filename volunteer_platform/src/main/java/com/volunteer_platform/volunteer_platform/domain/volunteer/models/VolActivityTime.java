package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityTimeStatus;
import com.volunteer_platform.volunteer_platform.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VolActivityTime extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "vol_activity_time_id")
    private Long id;

    private LocalDate activityDate;

    @Enumerated(EnumType.STRING)
    private DayOfWeek activityWeek;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vol_activity_id")
    private VolActivity volActivity;

    private Integer numOfApplicant;

    private Integer numOfRecruit;

    @Builder.Default
    @OneToMany(mappedBy = "volActivityTime")
    private List<AppHistory> appHistories = new ArrayList<>();

    private int startTime;

    private int endTime;

    @Enumerated(EnumType.STRING)
    private ActivityTimeStatus timeStatus;
}

