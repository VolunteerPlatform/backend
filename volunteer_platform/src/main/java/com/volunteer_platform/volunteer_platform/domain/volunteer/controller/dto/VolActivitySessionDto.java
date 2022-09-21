package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VolActivitySessionDto {

    private Long sessionId;

    private LocalDate activityDate;

    private int startTime;

    private int endTime;

    private int numOfApplicant;

    private int numOfRecruit;

    private SessionStatus sessionStatus;

    public static VolActivitySessionDto of(VolActivitySession volActivitySession) {
        return new VolActivitySessionDto(
                volActivitySession.getId(),
                volActivitySession.getActivityDate(),
                volActivitySession.getStartTime(),
                volActivitySession.getEndTime(),
                volActivitySession.getNumOfApplicant(),
                volActivitySession.getNumOfRecruit(),
                volActivitySession.getSessionStatus()
        );
    }
}
