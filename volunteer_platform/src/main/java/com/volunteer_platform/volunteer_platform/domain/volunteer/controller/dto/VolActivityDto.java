package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Period;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Review;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class VolActivityDto {

    private Long id;

    private ActivityType activityType;

    private ActivityMethod activityMethod;

    private AuthorizationType authorizationType;

    private String activityName;

    private String organName;

    private List<VolActivityDayOfWeekDto> activityDayofWeeks;

    private String activitySummary;

    private String activityContent;

    private List<Review> reviewList;

    private Long rating;

    private Period activityPeriod;

    private Period activityRecruitPeriod;

    private Integer numOfRecruit;

    public static VolActivityDto of(VolActivity volActivity) {
        return VolActivityDto.builder()
                .id(volActivity.getId())
                .activityType(volActivity.getActivityType())
                .activityMethod(volActivity.getActivityMethod())
                .authorizationType(volActivity.getAuthorizationType())
                .activityName(volActivity.getActivityName())
                .organName(volActivity.getVolOrgan().getName())
                .activityDayofWeeks(volActivity.getDayOfWeeks().stream().map(VolActivityDayOfWeekDto::of).collect(Collectors.toList()))
                .activitySummary(volActivity.getActivitySummary())
                .activityContent(volActivity.getActivityContent())
                .reviewList(volActivity.getReviews())
                .rating(0L)
                .activityPeriod(volActivity.getActivityPeriod())
                .activityRecruitPeriod(volActivity.getActivityRecruitPeriod())
                .numOfRecruit(volActivity.getNumOfRecruit())
                .build();
    }
}

