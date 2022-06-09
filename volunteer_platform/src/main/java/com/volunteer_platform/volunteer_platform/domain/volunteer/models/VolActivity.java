package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Category;
import com.volunteer_platform.volunteer_platform.global.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class VolActivity extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "vol_activity_id")
    private Long id;

    @OneToMany(mappedBy = "volActivity")
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vol_organ_id")
    private VolOrgan volOrgan;

    @OneToMany(mappedBy = "volActivity")
    private List<VolActivityTime> activityTimes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "volActivity", cascade = CascadeType.ALL)
    private List<VolActivityDayOfWeek> dayOfWeeks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @Enumerated(EnumType.STRING)
    private ActivityMethod activityMethod;

    @Enumerated(EnumType.STRING)
    private AuthorizationType authorizationType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "begin", column = @Column(name = "ACTIVITY_START")),
            @AttributeOverride(name = "end", column = @Column(name = "ACTIVITY_END")),
    })
    private Period activityPeriod;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "begin", column = @Column(name = "RECRUIT_START")),
            @AttributeOverride(name = "end", column = @Column(name = "RECRUIT_END")),
    })
    private Period activityRecruitPeriod;

    private String activityName;
    private String activitySummary;
    private String activityContent;

    private Integer numOfRecruit;

    @Enumerated(EnumType.STRING)
    private Category category;
}
