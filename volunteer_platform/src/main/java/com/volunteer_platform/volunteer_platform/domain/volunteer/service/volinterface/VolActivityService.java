package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;

import java.util.List;

public interface VolActivityService {
    VolActivity createVolActivity(ActivityForm activityForm, VolOrgan volOrgan);

    List<VolActivity> findActivitiesByOrgan(Long organId);

    VolActivity findActivityById(Long activityId);
}
