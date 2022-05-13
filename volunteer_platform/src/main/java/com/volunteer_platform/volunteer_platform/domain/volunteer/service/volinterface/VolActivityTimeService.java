package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;

public interface VolActivityTimeService {

    public void saveVolActivityTime(VolActivityTime volActivityTime);

    void createVolActivityTime(Form form, VolActivity volActivity);
}
