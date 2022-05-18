package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Week;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VolActivityTimeForm {

    private int startTime;
    private int endTime;
    private List<Week> activityWeek;
}
