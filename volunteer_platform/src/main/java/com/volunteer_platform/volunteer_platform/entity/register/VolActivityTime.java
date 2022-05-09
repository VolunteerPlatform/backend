package com.volunteer_platform.volunteer_platform.entity.register;

import com.volunteer_platform.volunteer_platform.entity.BaseEntity;
import com.volunteer_platform.volunteer_platform.entity.member.Week;
import lombok.*;

import javax.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vol_activity_id")
    private VolActivity volActivity;

    @Enumerated(EnumType.STRING)
    private Week activityWeek;

    private int startTime;
    private int endTime;
}
