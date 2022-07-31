package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class VolActivitySessionDateAndTimeDto {

    private LocalDate date;

    private Integer startTime;

    private Integer endTime;

    public static VolActivitySessionDateAndTimeDto of(VolActivitySession volActivitySession) {
        return VolActivitySessionDateAndTimeDto.builder()
                .date(volActivitySession.getActivityDate())
                .startTime(volActivitySession.getStartTime())
                .endTime(volActivitySession.getEndTime())
                .build();
    }
}
