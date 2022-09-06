package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ApplicationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VolAppService {

    AppHistory volApply(Long sessionId, ApplicationForm applicationForm);

    AppHistory authorizeApplicant(Long applicationId, IsAuthorized status);

    void cancelApplication(Long applicationId);

    List<AppHistory> fetchApplications(Long memberId);

    List<AppHistory> fetchApplicationsByCondition(Long activityTimeId, IsAuthorized status, Pageable pageable);

}
