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
                .activityDate(appHistory.getVolActivitySession().getActivityDate())
                .startTime(appHistory.getVolActivitySession().getStartTime())
                .endTime(appHistory.getVolActivitySession().getEndTime())
                .activityId(appHistory.getVolActivitySession().getVolActivity().getId())
                .activityName(appHistory.getVolActivitySession().getVolActivity().getActivityName())
                .activitySummary(appHistory.getVolActivitySession().getVolActivity().getActivitySummary())
                .organization(appHistory.getVolActivitySession().getVolActivity().getVolOrgan().getName())
                .build();
    }
}
