package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ApplicationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.PrivacyApproval;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VolAppService {

    private final VolAppRepository volAppRepository;
    private final MemberRepository memberRepository;
    private final VolActivitySessionRepository volActivitySessionRepository;

    private static final int CANCELABLE_BEFORE_DAYS = 3;

    public AppHistory volApply(Long sessionId, ApplicationForm applicationForm) {
        Member applicant = findMemberById(applicationForm.getMemberId());
        VolActivitySession activitySession = findActivitySessionById(sessionId);

        if (isMemberAlreadyApplied(applicationForm.getMemberId(), sessionId)) {
            throw new IllegalStateException("이미 해당 세션에 지원하였습니다.");
        }

        if (!isApplicableSession(activitySession)) {
            throw new IllegalStateException("해당 세션은 신청 가능한 상태가 아닙니다.");
        }

        if (applicationForm.getPrivacyApproval() != PrivacyApproval.AGREE) {
            throw new IllegalArgumentException("개인정보 제공 미동의시 봉사활동을 신청할 수 없습니다.");
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

    public void cancelApplication(Long applicationId) {
        AppHistory application = findApplication(applicationId);

        if (application.getIsAuthorized() == IsAuthorized.COMPLETE) {
            throw new IllegalStateException("활동이 완료된 활동은 취소가 불가능합니다.");
        }

        // 활동일 기준 CANCELABLE_BEFORE_DAYS 일 이전이어야 취소 가능
        LocalDate activityDate = application.getVolActivitySession().getActivityDate();
        if (!isBeforeCancelableDays(activityDate)) {
            throw new IllegalStateException("봉사활동은 활동 시작일 기준 " + CANCELABLE_BEFORE_DAYS + "일전이어야 취소가 가능합니다.");
        }

        AuthorizationType authorizationType = application.getVolActivitySession().getVolActivity().getAuthorizationType();
        boolean isImmediateApprovalActivity = authorizationType == AuthorizationType.UNNECESSARY;

        if (!isCancelableAuthorizationStatus(application.getIsAuthorized(), isImmediateApprovalActivity)) {
            throw new IllegalStateException("신청 취소가 가능한 승인 상태가 아닙니다.");
        }

        volAppRepository.deleteById(applicationId);
    }

    private boolean isBeforeCancelableDays(LocalDate activityDate) {
        LocalDate today = LocalDate.now();
        return today.plusDays(CANCELABLE_BEFORE_DAYS).compareTo(activityDate) < 0;
    }

    private boolean isCancelableAuthorizationStatus(IsAuthorized isAuthorized, boolean isImmediateApprovalActivity) {
        if (isAuthorized == IsAuthorized.DISAPPROVAL || isAuthorized == IsAuthorized.WAITING) {
            return true;
        }

        if (isImmediateApprovalActivity) {
            return isAuthorized == IsAuthorized.APPROVAL;
        }

        return false;
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
