package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class VolActivityTimeDateAndTimeDto {

    private LocalDate date;

    private Integer startTime;

    private Integer endTime;

    public static VolActivityTimeDateAndTimeDto of(VolActivityTime volActivityTime) {
        return VolActivityTimeDateAndTimeDto.builder()
                .date(volActivityTime.getActivityDate())
                .startTime(volActivityTime.getStartTime())
                .endTime(volActivityTime.getEndTime())
                .build();
    }
}
