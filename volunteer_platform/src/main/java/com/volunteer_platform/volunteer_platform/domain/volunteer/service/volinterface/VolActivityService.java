package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolActivityForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;

public interface VolActivityService {

    void saveVolActivity(VolActivity volActivity);

    VolActivity createVolActivity(VolActivityForm form, VolOrgan volOrgan);
}
