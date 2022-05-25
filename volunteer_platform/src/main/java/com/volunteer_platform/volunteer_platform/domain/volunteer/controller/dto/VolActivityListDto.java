package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class VolActivityListDto {
    public Long id;

    public String activityName;

    public String organization;

    public List<VolActivityTimeDto> activityTimes;

    public String activitySummary;

    public Long score;

    public Integer numOfRecruit;

    public Integer numOfApplicant;

    public Integer numOfAccepted;

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
