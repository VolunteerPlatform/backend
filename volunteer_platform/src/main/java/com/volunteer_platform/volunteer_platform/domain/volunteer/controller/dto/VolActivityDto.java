package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Period;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolActivityDto {

    private Long id;

    private ActivityMethod activityMethod;

    private AuthorizationType authorizationType;

    private String activityName;

    private Long organizationId;

    private String organization;

    private List<VolActivityDayOfWeekDto> activityDayofWeeks;

    private String activitySummary;

    private String activityContent;

    private Period activityPeriod;

    private Period activityRecruitPeriod;

    private Category category;

    @Getter
    @JsonProperty(value = "isDeleted")
    private boolean deleted;

    public static VolActivityDto of(VolActivity volActivity) {
        return VolActivityDto.builder()
                .id(volActivity.getId())
                .activityMethod(volActivity.getActivityMethod())
                .authorizationType(volActivity.getAuthorizationType())
                .activityName(volActivity.getActivityName())
                .organization(volActivity.getVolOrgan().getName())
                .organizationId(volActivity.getVolOrgan().getId())
                .activityDayofWeeks(volActivity.getDayOfWeeks().stream().map(VolActivityDayOfWeekDto::of).collect(Collectors.toList()))
                .activitySummary(volActivity.getActivitySummary())
                .activityContent(volActivity.getActivityContent())
                .activityPeriod(volActivity.getActivityPeriod())
                .activityRecruitPeriod(volActivity.getActivityRecruitPeriod())
                .category(volActivity.getCategory())
                .deleted(volActivity.isDeleted())
                .build();
    }
}

