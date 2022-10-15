package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Period;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityForm {
    private String activityName;
    private String activitySummary;
    private String activityContent;
    private ActivityMethod activityMethod;
    private AuthorizationType authorizationType;
    private String activityBegin;
    private String activityEnd;
    private String recruitBegin;
    private String recruitEnd;
    private Category category;
    private Long organizationId;
    private List<ActivityTimeForm> timeList;

    public VolActivity toEntity(VolOrgan volOrgan) {
        return VolActivity.builder()
                .activityName(this.getActivityName())
                .activitySummary(this.getActivitySummary())
                .activityContent(this.getActivityContent())
                .activityMethod(this.getActivityMethod())
                .authorizationType(this.getAuthorizationType())
                .category(this.getCategory())
                .activityPeriod(new Period(this.getActivityBegin(), this.getActivityEnd()))
                .activityRecruitPeriod(new Period(this.getRecruitBegin(), this.getRecruitEnd()))
                .volOrgan(volOrgan)
                .build();
    }
}
