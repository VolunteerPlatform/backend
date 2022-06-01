package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class VolActivitySummaryDto {
    private Long id;

    private String activityName;

    private String organization;

    private List<VolActivityDayOfWeekDto> activityDayOfWeeks;

    private String activitySummary;

    private Long score;

    private Integer numOfRecruit;

    public static VolActivitySummaryDto of(VolActivity volActivity) {
        return VolActivitySummaryDto.builder()
                .id(volActivity.getId())
                .activityName(volActivity.getActivityName())
                .organization(volActivity.getVolOrgan().getName())
                .activityDayOfWeeks(volActivity.getDayOfWeeks().stream().map(VolActivityDayOfWeekDto::of).collect(Collectors.toList()))
                .activitySummary(volActivity.getActivitySummary())
                .score(0L)
                .numOfRecruit(volActivity.getNumOfRecruit())
                .build();
    }
}
