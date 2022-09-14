package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationForm;

public interface VolOrganService {

    VolOrganIdDto createVolOrgan(OrganizationForm organizationForm);

    void deleteOrgan(Long organId);

    VolOrganDto findOrgan(Long organId);
}
