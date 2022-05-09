package com.volunteer_platform.volunteer_platform.controller.form;

import com.volunteer_platform.volunteer_platform.entity.member.Week;
import lombok.Getter;

import java.util.List;

@Getter
public class VolActivityTimeForm {

    private int startTime;
    private int endTime;
    private List<Week> activityWeek;
}
