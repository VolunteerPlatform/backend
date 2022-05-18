package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolActivityTimeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;

import java.util.List;

public interface VolActivityTimeService {

    public void saveVolActivityTime(VolActivityTime volActivityTime);

    void createVolActivityTime(List<VolActivityTimeForm> volActivityTimeForms, VolActivity volActivity);
}
