package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.VolAppForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivityTime;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivityTimeRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class VolAppService {

    private final VolAppRepository volAppRepository;
    private final MemberRepository memberRepository;
    private final VolActivityTimeRepository activityTimeRepository;

    public void volApply(VolAppForm volAppForm) {
        Member applicant = memberRepository.findById(volAppForm.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID 입니다."));

        LocalDate activityDate = LocalDate.parse(volAppForm.getActivityDate());
        VolActivityTime activityTime = activityTimeRepository.findByActivityDateAndVolActivityId(activityDate, volAppForm.getActivityId())
                .orElseThrow(() -> new IllegalArgumentException("해당 봉사활동이 존재하지 않거나, 해당 날짜에 활동하지 않는 봉사입니다."));

        AppHistory appHistory = AppHistory.builder()
                .member(applicant)
                .comment(volAppForm.getComment())
                .privacyApproval(volAppForm.getPrivacyApproval())
                .volActivityTime(activityTime)
                .isAuthorized(IsAuthorized.WAITING)
                .build();

        volAppRepository.save(appHistory);
    }

    // 봉사 승인
    public void acceptApplicant(Long applicationId) {
        AppHistory application = volAppRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청 ID 입니다."));

        application.approve();
    }

    // 봉사 거절
    public void denyApplicant(Long applicationId) {
        AppHistory application = volAppRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청 ID 입니다."));

        application.deny();
    }

    // 봉사 승인/거절 취소
    public void pendApplicant(Long applicationId) {
        AppHistory application = volAppRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청 ID 입니다."));

        application.pend();
    }
}
