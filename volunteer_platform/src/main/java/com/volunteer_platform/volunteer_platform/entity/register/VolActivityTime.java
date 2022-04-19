package com.volunteer_platform.volunteer_platform.entity.register;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VolActivityTime {

    @Id @GeneratedValue
    @Column(name = "vol_activity_time_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vol_activity_id")
    private VolActivity volActivity;

    private int activityWeek;
    private int startTime;
    private int endTime;
}
