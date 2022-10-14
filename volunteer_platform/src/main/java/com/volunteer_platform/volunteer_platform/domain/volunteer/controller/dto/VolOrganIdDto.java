package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import lombok.Getter;

@Getter
public class VolOrganIdDto {

    private Long organizationId;

    public VolOrganIdDto(Long organId) {
        this.organizationId = organId;
    }
}
