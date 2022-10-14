package com.volunteer_platform.volunteer_platform.domain.timetable.form;

import lombok.Getter;

import java.time.DayOfWeek;

@Getter
public class TimeTableElementForm {

    private DayOfWeek dayOfWeek;
    private int startTime;
    private int endTime;
}
