package com.volunteer_platform.volunteer_platform.domain.timetable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@AllArgsConstructor
public class TimeTableDto {

    private DayOfWeek dayOfWeek;
    private int startTime;
    private int endTime;

}
