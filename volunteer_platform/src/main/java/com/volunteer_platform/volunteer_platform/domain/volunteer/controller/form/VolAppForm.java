package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.PrivacyApproval;
import lombok.Getter;

@Getter
public class VolAppForm {

    public Long memberId;

    public String comment;

    public String activityDate;

    public PrivacyApproval privacyApproval;
}
