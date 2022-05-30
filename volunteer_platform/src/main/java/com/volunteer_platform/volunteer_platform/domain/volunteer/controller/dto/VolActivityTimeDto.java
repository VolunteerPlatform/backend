package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@Builder
public class VolActivityTimeDto {

    private DayOfWeek week;

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
