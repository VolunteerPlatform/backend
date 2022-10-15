package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Category;
import lombok.Getter;

@Getter
public class ActivityModifyForm {
    private String activityName;
    private String activitySummary;
    private String activityContent;
    private ActivityMethod activityMethod;
    private AuthorizationType authorizationType;
    private Category category;
}
