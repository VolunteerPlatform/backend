package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VolActivityIdDto {

    private Long activityId;

    public VolActivityIdDto(Long activityId) {
        this.activityId = activityId;
    }
}
