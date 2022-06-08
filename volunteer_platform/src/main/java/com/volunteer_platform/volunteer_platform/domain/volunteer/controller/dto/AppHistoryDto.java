package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [봉사 승인상태(isAuthorized)] 활동 날짜/시간
 * 카테고리 목록
 * 봉사 활동 이름 -> 누르면 봉사 활동 상세보기로 이동해야 하니 ID를 주어야 할듯
 * 모집 기관
 * 봉사 활동 요일
 * 봉사 활동 시간
 * 봉사 주요 활동
 * 활동 평점
 * 후기 등록여부(등록시 TRUE 아니면 FALSE)
 */
@Builder
public class AppHistoryDto {
    public Long applicationId;

    public IsAuthorized isAuthorized;

    public LocalDate activityDate;

    public Integer startTime;

    public Integer endTime;

    public Long activityId;

    public String activityName;

    public String activitySummary;

    public String organization;

    public List<VolActivityDayOfWeekDto> activityDayOfWeekAndTimes;

    public Long rating;

    public Boolean isReviewed;

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
                .activityDayOfWeekAndTimes(appHistory.getVolActivityTime().getVolActivity().getDayOfWeeks().stream().map(VolActivityDayOfWeekDto::of).collect(Collectors.toList()))
                .rating(0L)
                .isReviewed(Boolean.FALSE)
                .build();
    }
}
