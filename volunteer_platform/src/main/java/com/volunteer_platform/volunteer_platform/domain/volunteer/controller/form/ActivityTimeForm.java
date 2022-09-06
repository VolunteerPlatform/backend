package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityDayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityTimeForm {

    private int startTime;
    private int endTime;
    private DayOfWeek activityWeek;

    public VolActivityDayOfWeek toEntity(VolActivity volActivity) {
        return VolActivityDayOfWeek.builder()
                .startTime(this.startTime)
                .endTime(this.endTime)
                .activityWeek(this.activityWeek)
                .volActivity(volActivity)
                .build();
    }
}
