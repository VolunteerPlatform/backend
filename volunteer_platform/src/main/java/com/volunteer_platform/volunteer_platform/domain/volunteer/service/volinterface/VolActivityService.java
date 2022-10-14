package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.SearchCondition;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivitySessionDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityModifyForm;

import java.time.LocalDate;
import java.util.List;

public interface VolActivityService {
    VolActivityIdDto createVolActivity(ActivityForm activityForm);

    List<VolActivityDto> findActivitiesByOrgan(Long organId);

    VolActivityDto findActivityById(Long activityId);

    List<SearchResultDto> searchActivity(Long memberId, SearchCondition searchCondition);

    void deleteActivity(Long activityId);

    List<VolActivitySessionDto> findSessionsOfActivity(Long activityId, LocalDate activityDate);

    void editActivity(Long activityId, ActivityModifyForm activityModifyForm);
}
