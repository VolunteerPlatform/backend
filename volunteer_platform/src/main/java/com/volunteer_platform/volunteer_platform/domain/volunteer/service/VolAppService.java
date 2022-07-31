package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ApplicationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VolAppService {

    private final VolAppRepository volAppRepository;
    private final MemberRepository memberRepository;
    private final VolActivitySessionRepository volActivitySessionRepository;

    public AppHistory volApply(Long activityTimeId, ApplicationForm applicationForm) {
        Member applicant = memberRepository.findById(applicationForm.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID 입니다."));

        VolActivitySession activitySession = volActivitySessionRepository.findById(activityTimeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 봉사활동 타임 정보가 존재하지 않습니다."));

        AppHistory appHistory = AppHistory.builder()
                .member(applicant)
                .comment(applicationForm.getComment())
                .privacyApproval(applicationForm.getPrivacyApproval())
                .volActivitySession(activitySession)
                .isAuthorized(IsAuthorized.WAITING)
                .build();

        volAppRepository.save(appHistory);
        return appHistory;
    }

    // 지원자 승인/거절/대기 상태 변경
    public AppHistory authorizeApplicant(Long applicationId, IsAuthorized status) {
        AppHistory application = findApplication(applicationId);
        application.setIsAuthorized(status);

        return application;
    }

    private AppHistory findApplication(Long applicationId) {
        return volAppRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청 ID 입니다."));
    }

    public List<AppHistory> fetchApplications(Long memberId) {
        return volAppRepository.findByMemberId(memberId);
    }

    public List<AppHistory> fetchApplicationsByCondition(Long activityTimeId, IsAuthorized status, Pageable pageable) {
        return volAppRepository.findApplicantsByCondition(activityTimeId, status, pageable);
    }
}
