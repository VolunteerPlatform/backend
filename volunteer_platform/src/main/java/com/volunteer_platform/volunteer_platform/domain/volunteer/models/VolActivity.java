package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityModifyForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vol_organ_id")
    private VolOrgan volOrgan;

    @OneToMany(mappedBy = "volActivity")
    private List<VolActivitySession> activitySessions = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "volActivity", cascade = CascadeType.ALL)
    private List<VolActivityDayOfWeek> dayOfWeeks = new ArrayList<>();

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

    @Enumerated(EnumType.STRING)
    private Category category;

    @Builder.Default
    private boolean isDeleted = false;

    public void delete() {
        isDeleted = true;
    }

    public void modify(ActivityModifyForm modifyForm) {
        this.activityName = modifyForm.getActivityName();
        this.activityContent = modifyForm.getActivityContent();
        this.activitySummary = modifyForm.getActivitySummary();
        this.activityMethod = modifyForm.getActivityMethod();
        this.authorizationType = modifyForm.getAuthorizationType();
        this.category = modifyForm.getCategory();
    }
}
