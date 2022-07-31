package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;

public interface VolOrganService {

    void saveVolOrgan(VolOrgan volOrgan);

    VolOrgan createVolOrgan(OrganizationForm organizationForm);

    void deleteOrgan(Long organId);

    VolOrgan findOrgan(Long organId);
}
