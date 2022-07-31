package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityType;
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
    private ActivityType activityType;
    private AuthorizationType authorizationType;
    private String activityBegin;
    private String activityEnd;
    private String recruitBegin;
    private String recruitEnd;
    private Integer numOfRecruit;
    private Category category;
    private Long organizationId;
    private List<VolActivityTimeForm> timeList;
}
