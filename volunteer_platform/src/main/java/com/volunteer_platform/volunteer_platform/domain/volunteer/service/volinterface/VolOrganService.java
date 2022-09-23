package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.VolOrganIdDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.OrganizationForm;

import java.util.List;

public interface VolOrganService {

    VolOrganIdDto createVolOrgan(OrganizationForm organizationForm, Long memberId);

    void deleteOrgan(Long organId);

    VolOrganDto findOrgan(Long organId);

    List<VolOrganDto> findOrganByMember(Long memberId);
}
