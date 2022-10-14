package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.AppHistoryDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.ApplicantDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ApplicationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VolAppService {

    AppHistoryDto volApply(Long memberId, Long sessionId, ApplicationForm applicationForm);

    AppHistoryDto authorizeApplicant(Long applicationId, IsAuthorized status);

    void cancelApplication(Long applicationId);

    List<AppHistoryDto> fetchApplications(Long memberId);

    List<ApplicantDto> fetchApplicationsByCondition(Long activityTimeId, IsAuthorized status, Pageable pageable);

}
