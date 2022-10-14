package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityDayOfWeek;
import lombok.*;

import java.time.DayOfWeek;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VolActivityDayOfWeekDto {
    private DayOfWeek week;
    private Integer startTime;
    private Integer endTime;
    private Integer numOfRecruit;

    public static VolActivityDayOfWeekDto of(VolActivityDayOfWeek volActivityDayOfWeek) {
        return VolActivityDayOfWeekDto.builder()
                .week(volActivityDayOfWeek.getActivityWeek())
                .startTime(volActivityDayOfWeek.getStartTime())
                .endTime(volActivityDayOfWeek.getEndTime())
                .numOfRecruit(volActivityDayOfWeek.getNumOfRecruit())
                .build();
    }
}
