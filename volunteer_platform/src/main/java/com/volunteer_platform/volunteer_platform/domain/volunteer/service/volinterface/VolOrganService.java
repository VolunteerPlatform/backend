package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.Form;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;

public interface VolOrganService {

    void saveVolOrgan(VolOrgan volOrgan);

    VolOrgan createVolOrgan(Form form);
}
