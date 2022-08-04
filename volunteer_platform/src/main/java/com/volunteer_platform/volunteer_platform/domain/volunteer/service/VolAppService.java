package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ApplicationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
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

    public AppHistory volApply(Long sessionId, ApplicationForm applicationForm) {
        Member applicant = findMemberById(applicationForm.getMemberId());
        VolActivitySession activitySession = findActivitySessionById(sessionId);

        if (isMemberAlreadyApplied(applicationForm.getMemberId(), sessionId)) {
            throw new IllegalStateException("이미 해당 세션에 지원하였습니다.");
        }

        if (!isApplicableSession(activitySession)) {
            throw new IllegalStateException("해당 세션은 신청 가능한 상태가 아닙니다.");
        }

        AuthorizationType authorizationType = activitySession.getVolActivity().getAuthorizationType();
        boolean isImmediateApprovalActivity = authorizationType == AuthorizationType.UNNECESSARY;

        AppHistory appHistory = AppHistory.builder()
                .member(applicant)
                .comment(applicationForm.getComment())
                .privacyApproval(applicationForm.getPrivacyApproval())
                .volActivitySession(activitySession)
                .isAuthorized(isImmediateApprovalActivity ? IsAuthorized.APPROVAL : IsAuthorized.WAITING)
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

    public List<AppHistory> fetchApplications(Long memberId) {
        return volAppRepository.findByMemberId(memberId);
    }

    public List<AppHistory> fetchApplicationsByCondition(Long activityTimeId, IsAuthorized status, Pageable pageable) {
        return volAppRepository.findApplicantsByCondition(activityTimeId, status, pageable);
    }

    private AppHistory findApplication(Long applicationId) {
        return volAppRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 신청 ID 입니다."));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID 입니다."));
    }

    private VolActivitySession findActivitySessionById(Long sessionId) {
        return volActivitySessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 봉사활동 타임 정보가 존재하지 않습니다."));
    }

    private boolean isMemberAlreadyApplied(Long memberId, Long sessionId) {
        return volAppRepository.existsByMemberIdAndVolActivitySessionId(memberId, sessionId);
    }

    private boolean isApplicableSession(VolActivitySession volActivitySession) {
        if (volActivitySession.getSessionStatus() != SessionStatus.RECRUITING) {
            return false;
        }

        if (volActivitySession.getNumOfApplicant() >= volActivitySession.getNumOfRecruit()) {
            return false;
        }

        return true;
    }
}
