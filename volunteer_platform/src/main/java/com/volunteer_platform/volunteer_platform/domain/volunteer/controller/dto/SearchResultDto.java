package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;


import com.querydsl.core.annotations.QueryProjection;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SearchResultDto {

    private Long activityId;

    private Long activitySessionId;

    private Long organizationId;

    private String activityName;

    private String organizationName;

    private LocalDate activityDate;

    private int startTime;

    private int endTime;

    private Category category;

    private ActivityMethod activityMethod;

    private Double longitude;

    private Double latitude;

    public static SearchResultDto of(VolActivitySession activitySession) {
        return SearchResultDto.builder()
                .activitySessionId(activitySession.getId())
                .activityId(activitySession.getVolActivity().getId())
                .organizationId(activitySession.getVolActivity().getVolOrgan().getId())
                .activityName(activitySession.getVolActivity().getActivityName())
                .organizationName(activitySession.getVolActivity().getVolOrgan().getName())
                .activityDate(activitySession.getActivityDate())
                .startTime(activitySession.getStartTime())
                .endTime(activitySession.getEndTime())
                .category(activitySession.getVolActivity().getCategory())
                .activityMethod(activitySession.getVolActivity().getActivityMethod())
                .longitude(activitySession.getVolActivity().getVolOrgan().getAddress().getCoordinate().getLongitude())
                .latitude(activitySession.getVolActivity().getVolOrgan().getAddress().getCoordinate().getLatitude())
                .build();
    }

    @QueryProjection
    public SearchResultDto(Long activityId, Long activitySessionId, Long organizationId,
                           String activityName, String organizationName, LocalDate activityDate,
                           int startTime, int endTime, Category category,
                           ActivityMethod activityMethod, Double longitude, Double latitude) {

        this.activityId = activityId;
        this.activitySessionId = activitySessionId;
        this.organizationId = organizationId;
        this.activityName = activityName;
        this.organizationName = organizationName;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.activityMethod = activityMethod;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}

