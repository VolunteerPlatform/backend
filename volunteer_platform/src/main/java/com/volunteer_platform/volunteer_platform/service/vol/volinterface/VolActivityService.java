package com.volunteer_platform.volunteer_platform.service.vol.volinterface;

import com.volunteer_platform.volunteer_platform.controller.form.Form;
import com.volunteer_platform.volunteer_platform.entity.register.VolActivity;
import com.volunteer_platform.volunteer_platform.entity.register.VolOrgan;

public interface VolActivityService {

    void saveVolActivity(VolActivity volActivity);

    VolActivity createVolActivity(Form form, VolOrgan volOrgan);
}
