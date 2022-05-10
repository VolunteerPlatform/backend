package com.volunteer_platform.volunteer_platform.service.vol.volinterface;

import com.volunteer_platform.volunteer_platform.controller.form.Form;
import com.volunteer_platform.volunteer_platform.entity.register.VolOrgan;

public interface VolOrganService {

    void saveVolOrgan(VolOrgan volOrgan);

    VolOrgan createVolOrgan(Form form);
}
