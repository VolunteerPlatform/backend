package com.volunteer_platform.volunteer_platform.domain.timetable.dto;

import com.volunteer_platform.volunteer_platform.domain.timetable.models.TimeTable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;

@Getter
@AllArgsConstructor
public class TimeTableElementDto {

    private DayOfWeek dayOfWeek;
    private int startTime;
    private int endTime;

    public static TimeTableElementDto of(TimeTable timeTable) {
        return new TimeTableElementDto(timeTable.getDayOfWeek(), timeTable.getStartTime(), timeTable.getEndTime());
    }
}
