package com.volunteer_platform.volunteer_platform.entity.register;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ActivityRecruitTerm {

    private String activityRecruitBegin;
    private String activityRecruitEnd;
}
