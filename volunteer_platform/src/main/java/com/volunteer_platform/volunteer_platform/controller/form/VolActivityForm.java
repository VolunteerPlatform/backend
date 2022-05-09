package com.volunteer_platform.volunteer_platform.controller.form;

import com.volunteer_platform.volunteer_platform.entity.register.Period;
import com.volunteer_platform.volunteer_platform.entity.register.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.entity.register.enumtype.ActivityType;
import com.volunteer_platform.volunteer_platform.entity.register.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.entity.register.enumtype.Category;
import lombok.Getter;

@Getter
public class VolActivityForm {

    private String activityName;
    private String activitySummary;
    private String activityContent;
    private ActivityMethod activityMethod;
    private ActivityType activityType;
    private AuthorizationType authorizationType;
    //todo LocalDateTime 으로 받기
    //private String activityBegin;
    //private String activityEnd;
    //private String  recruitBegin;
    //private String recruitEnd;
    private Category category;
}
