package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.PrivacyApproval;
import lombok.Builder;
import lombok.Getter;

/*
 * 성명
 * 생년월일
 * 1365 ID(소속센터)
 * 활동일자
 * 활동시간
 * 총 시간
 * 연락처
 * 정보제공동의
 * 승인 여부(isAuthorized)
 */
@Getter
@Builder
public class ApplicantDto {

    private Long applicationId;

    private String name;

    private String birthday;

    private String idOf1365;

    private String centerName;

    private VolActivityTimeDateAndTimeDto activityTime;

    private Integer totalActivityTime;

    private String phoneNumber;

    private PrivacyApproval privacyApproval;

    private IsAuthorized isAuthorized;

    public static ApplicantDto of(AppHistory appHistory) {
        return ApplicantDto.builder()
                .applicationId(appHistory.getId())
                .name(appHistory.getMember().getMemberInfo().getUserRealName())
                .birthday(appHistory.getMember().getMemberInfo().getBirthday())
                .idOf1365(appHistory.getMember().getMember1365Info().getIdOf1365())
                .centerName(appHistory.getMember().getMember1365Info().getCenterName())
                .activityTime(VolActivityTimeDateAndTimeDto.of(appHistory.getVolActivityTime()))
                .totalActivityTime(appHistory.getVolActivityTime().getEndTime() - appHistory.getVolActivityTime().getStartTime())
                .phoneNumber(appHistory.getMember().getMemberInfo().getPhoneNumber())
                .privacyApproval(appHistory.getPrivacyApproval())
                .isAuthorized(appHistory.getIsAuthorized())
                .build();
    }
}
