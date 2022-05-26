package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class VolActivityListDto {
    private Long id;

    private String activityName;

    private String organization;

    private List<VolActivityTimeDto> activityTimes;

    private String activitySummary;

    private Long score;

    private Integer numOfRecruit;

    private Integer numOfApplicant;

    private Integer numOfAccepted;

    public static VolActivityListDto of(VolActivity volActivity) {
        return VolActivityListDto.builder()
                .id(volActivity.getId())
                .activityName(volActivity.getActivityName())
                .organization(volActivity.getVolOrgan().getName())
                .activityTimes(volActivity.getActivityTimes().stream().map(VolActivityTimeDto::of).collect(Collectors.toList()))
                .activitySummary(volActivity.getActivitySummary())
                .score(0L)
                .numOfRecruit(20)
                .numOfAccepted(10)
                .build();
    }
}
