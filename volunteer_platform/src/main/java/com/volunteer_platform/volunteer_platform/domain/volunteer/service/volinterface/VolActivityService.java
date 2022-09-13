package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.SearchCondition;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolActivityIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;

import java.util.List;

public interface VolActivityService {
    VolActivityIdDto createVolActivity(ActivityForm activityForm, VolOrgan volOrgan);

    List<VolActivityDto> findActivitiesByOrgan(Long organId);

    VolActivityDto findActivityById(Long activityId);

    List<SearchResultDto> searchActivity(SearchCondition searchCondition);
}
