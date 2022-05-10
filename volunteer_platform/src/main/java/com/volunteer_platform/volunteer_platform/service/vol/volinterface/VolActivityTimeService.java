package com.volunteer_platform.volunteer_platform.service.vol.volinterface;

import com.volunteer_platform.volunteer_platform.controller.form.Form;
import com.volunteer_platform.volunteer_platform.entity.register.VolActivity;
import com.volunteer_platform.volunteer_platform.entity.register.VolActivityTime;

public interface VolActivityTimeService {

    public void saveVolActivityTime(VolActivityTime volActivityTime);

    void createVolActivityTime(Form form, VolActivity volActivity);
}
