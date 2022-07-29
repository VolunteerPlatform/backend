package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@Builder
public class AppHistoryDto {
    private Long applicationId;

    private IsAuthorized isAuthorized;

    private LocalDate activityDate;

    private Integer startTime;

    private Integer endTime;

    private Long activityId;

    private String activityName;

    private String activitySummary;

    private String organization;

    public static AppHistoryDto of(AppHistory appHistory) {
        return AppHistoryDto.builder()
                .applicationId(appHistory.getId())
                .isAuthorized(appHistory.getIsAuthorized())
                .activityDate(appHistory.getVolActivityTime().getActivityDate())
                .startTime(appHistory.getVolActivityTime().getStartTime())
                .endTime(appHistory.getVolActivityTime().getEndTime())
                .activityId(appHistory.getVolActivityTime().getVolActivity().getId())
                .activityName(appHistory.getVolActivityTime().getVolActivity().getActivityName())
                .activitySummary(appHistory.getVolActivityTime().getVolActivity().getActivitySummary())
                .organization(appHistory.getVolActivityTime().getVolActivity().getVolOrgan().getName())
                .build();
    }
}
