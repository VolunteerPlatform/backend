package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Week;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VolActivityTimeDto {

    private Week week;

    private Integer startTime;

    private Integer endTime;

    public static VolActivityTimeDto of(VolActivityTime volActivityTime) {
        return VolActivityTimeDto.builder()
                .week(volActivityTime.getActivityWeek())
                .startTime(volActivityTime.getStartTime())
                .endTime(volActivityTime.getEndTime())
                .build();
    }
}
