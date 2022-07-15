package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolActivityTimeForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;

import java.util.List;

public interface VolActivityTimeService {
    void createVolActivityTime(List<VolActivityTimeForm> volActivityTimeForms, VolActivity volActivity);
}
