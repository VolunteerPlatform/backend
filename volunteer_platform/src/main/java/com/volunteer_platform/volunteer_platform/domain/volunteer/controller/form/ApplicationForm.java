package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.PrivacyApproval;
import lombok.Getter;

@Getter
public class ApplicationForm {

    private Long memberId;

    private String comment;

    private PrivacyApproval privacyApproval;
}
