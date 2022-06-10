package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolActivityTimeForm {

    private int startTime;
    private int endTime;
    private DayOfWeek activityWeek;
}
