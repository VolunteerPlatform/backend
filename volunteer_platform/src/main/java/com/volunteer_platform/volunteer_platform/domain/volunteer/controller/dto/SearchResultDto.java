package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.ActivityMethod;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.Category;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SearchResultDto {

    private Long activityId;

    private Long sessionId;

    private Long organizationId;

    private String activityName;

    private String organization;

    private LocalDate activityDate;

    private int startTime;

    private int endTime;

    private Category category;

    private ActivityMethod activityMethod;

    private Double longitude;

    private Double latitude;

    @Builder.Default
    @JsonProperty("isWished")
    @Setter
    private boolean isWished = false;

    @QueryProjection
    public SearchResultDto(Long activityId, Long activitySessionId, Long organizationId,
                           String activityName, String organizationName, LocalDate activityDate,
                           int startTime, int endTime, Category category,
                           ActivityMethod activityMethod, Double longitude, Double latitude) {

        this.activityId = activityId;
        this.sessionId = activitySessionId;
        this.organizationId = organizationId;
        this.activityName = activityName;
        this.organization = organizationName;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
        this.activityMethod = activityMethod;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static SearchResultDto of(VolActivitySession activitySession) {
        return SearchResultDto.builder()
                .sessionId(activitySession.getId())
                .activityId(activitySession.getVolActivity().getId())
                .organizationId(activitySession.getVolActivity().getVolOrgan().getId())
                .activityName(activitySession.getVolActivity().getActivityName())
                .organization(activitySession.getVolActivity().getVolOrgan().getName())
                .activityDate(activitySession.getActivityDate())
                .startTime(activitySession.getStartTime())
                .endTime(activitySession.getEndTime())
                .category(activitySession.getVolActivity().getCategory())
                .activityMethod(activitySession.getVolActivity().getActivityMethod())
                .longitude(activitySession.getVolActivity().getVolOrgan().getAddress().getCoordinate().getLongitude())
                .latitude(activitySession.getVolActivity().getVolOrgan().getAddress().getCoordinate().getLatitude())
                .build();
    }
}

