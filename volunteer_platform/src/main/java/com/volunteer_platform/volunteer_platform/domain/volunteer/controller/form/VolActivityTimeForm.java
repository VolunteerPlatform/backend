package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Week;
import lombok.Getter;

import java.util.List;

@Getter
public class VolActivityTimeForm {

    private int startTime;
    private int endTime;
    private List<Week> activityWeek;
}
