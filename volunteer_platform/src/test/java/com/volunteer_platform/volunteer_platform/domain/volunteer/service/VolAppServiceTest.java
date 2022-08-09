package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ApplicationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolAppRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolAppServiceTest {

    @Mock
    VolActivitySessionRepository volActivitySessionRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private VolAppRepository volAppRepository;

    @InjectMocks
    private VolAppService volAppService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 봉사지원_정상처리() throws Exception {
        // given
        ApplicationForm applicationForm = getApplicationForm();

        Member member = Member.builder()
                .id(100L)
                .userName("HAN")
                .password("1234")
                .build();

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        VolActivity volActivity = VolActivity.builder()
                .authorizationType(AuthorizationType.NECESSARY)
                .build();

        VolActivitySession volActivitySession = VolActivitySession.builder()
                .id(101L)
                .volActivity(volActivity)
                .activityDate(LocalDate.of(2022, 05, 15))
                .startTime(13)
                .endTime(15)
                .numOfApplicant(0)
                .numOfRecruit(30)
                .activityWeek(DayOfWeek.SUNDAY)
                .sessionStatus(SessionStatus.RECRUITING)
                .build();

        when(volActivitySessionRepository.findById(anyLong()))
                .thenReturn(Optional.of(volActivitySession));

        when(volAppRepository.save(any(AppHistory.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        // when
        AppHistory appHistory = volAppService.volApply(101L, applicationForm);

        // then
        assertThat(appHistory.getMember().getId()).isEqualTo(applicationForm.getMemberId());
        assertThat(appHistory.getComment()).isEqualTo(applicationForm.getComment());
        assertThat(appHistory.getPrivacyApproval()).isEqualTo(applicationForm.getPrivacyApproval());
        assertThat(appHistory.getVolActivitySession().getId()).isEqualTo(volActivitySession.getId());
        assertThat(appHistory.getIsAuthorized()).isEqualTo(IsAuthorized.WAITING);
    }

    @Test
    void 봉사지원시_존재하지_않는_회원은_예외처리() throws Exception {
        // given
        ApplicationForm applicationForm = getApplicationForm();

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    volAppService.volApply(1L, applicationForm);
                }).withMessage("존재하지 않는 사용자 ID 입니다.");
    }

    @Test
    void 봉사지원시_존재하지_않는_시간은_예외처리() throws Exception {
        // given
        ApplicationForm applicationForm = getApplicationForm();

        Member member = Member.builder()
                .id(100L)
                .userName("HAN")
                .password("1234")
                .build();

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        when(volActivitySessionRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    volAppService.volApply(1L, applicationForm);
                }).withMessage("해당 봉사활동 타임 정보가 존재하지 않습니다.");
    }

    @Test
    void 채용중이지_않은_세션은_지원불가() throws Exception {
        // given
        ApplicationForm applicationForm = getApplicationForm();

        Member member = Member.builder()
                .id(100L)
                .userName("HAN")
                .password("1234")
                .build();

        VolActivitySession volActivitySession = VolActivitySession.builder()
                .id(101L)
                .volActivity(null)
                .activityDate(LocalDate.of(2022, 05, 15))
                .startTime(13)
                .endTime(15)
                .activityWeek(DayOfWeek.SUNDAY)
                .sessionStatus(SessionStatus.RECRUIT_CLOSED)
                .build();

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        when(volActivitySessionRepository.findById(any()))
                .thenReturn(Optional.of(volActivitySession));

        // then
        assertThatIllegalStateException()
                .isThrownBy(() -> {
                    volAppService.volApply(101L, applicationForm);
                }).withMessage("해당 세션은 신청 가능한 상태가 아닙니다.");
    }

    @Test
    void 이미_지원한_세션엔_지원불가() throws Exception {
        // given
        ApplicationForm applicationForm = getApplicationForm();

        Member member = Member.builder()
                .id(100L)
                .userName("HAN")
                .password("1234")
                .build();

        VolActivitySession volActivitySession = VolActivitySession.builder()
                .id(101L)
                .volActivity(null)
                .activityDate(LocalDate.of(2022, 05, 15))
                .startTime(13)
                .endTime(15)
                .activityWeek(DayOfWeek.SUNDAY)
                .sessionStatus(SessionStatus.RECRUIT_CLOSED)
                .build();

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        when(volActivitySessionRepository.findById(any()))
                .thenReturn(Optional.of(volActivitySession));

        when(volAppRepository.existsByMemberIdAndVolActivitySessionId(any(), any()))
                .thenReturn(Boolean.TRUE);

        // then
        assertThatIllegalStateException()
                .isThrownBy(() -> {
                    volAppService.volApply(101L, applicationForm);
                }).withMessage("이미 해당 세션에 지원하였습니다.");
    }

    @Test
    void 자동승인_봉사는_신청즉시_승인처리() throws Exception {
        // given
        ApplicationForm applicationForm = getApplicationForm();

        Member member = Member.builder()
                .id(100L)
                .userName("HAN")
                .password("1234")
                .build();

        VolActivity volActivity = VolActivity.builder()
                .authorizationType(AuthorizationType.UNNECESSARY)
                .build();

        VolActivitySession volActivitySession = VolActivitySession.builder()
                .id(101L)
                .volActivity(volActivity)
                .activityDate(LocalDate.of(2022, 05, 15))
                .startTime(13)
                .endTime(15)
                .numOfRecruit(30)
                .numOfApplicant(15)
                .activityWeek(DayOfWeek.SUNDAY)
                .sessionStatus(SessionStatus.RECRUITING)
                .build();

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        when(volActivitySessionRepository.findById(any()))
                .thenReturn(Optional.of(volActivitySession));

        // when
        AppHistory appHistory = volAppService.volApply(101L, applicationForm);

        // then
        assertThat(appHistory.getIsAuthorized()).isEqualTo(IsAuthorized.APPROVAL);
    }

    @Test
    void 개인정보_미동의시_신청불가() throws Exception {
        // given
        String volAppFormString = "{ \"memberId\" : 100, \"comment\" : \"열심히 하겠습니다!\", \"privacyApproval\" : \"DISAGREE\" }";
        ApplicationForm applicationForm = objectMapper.readValue(volAppFormString, ApplicationForm.class);

        Member member = Member.builder()
                .id(100L)
                .userName("HAN")
                .password("1234")
                .build();

        VolActivity volActivity = VolActivity.builder()
                .authorizationType(AuthorizationType.UNNECESSARY)
                .build();

        VolActivitySession volActivitySession = VolActivitySession.builder()
                .id(101L)
                .volActivity(volActivity)
                .activityDate(LocalDate.of(2022, 05, 15))
                .startTime(13)
                .endTime(15)
                .numOfRecruit(30)
                .numOfApplicant(15)
                .activityWeek(DayOfWeek.SUNDAY)
                .sessionStatus(SessionStatus.RECRUITING)
                .build();

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        when(volActivitySessionRepository.findById(any()))
                .thenReturn(Optional.of(volActivitySession));

        // then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> {
                    volAppService.volApply(101L, applicationForm);
                })
                .withMessage("개인정보 제공 미동의시 봉사활동을 신청할 수 없습니다.");
    }

    @Test
    void 봉사_승인() throws Exception {
        // given
        AppHistory appHistory = AppHistory.builder()
                .id(1L)
                .member(null)
                .comment(null)
                .privacyApproval(null)
                .volActivitySession(null)
                .isAuthorized(IsAuthorized.WAITING)
                .build();

        when(volAppRepository.findById(1L))
                .thenReturn(Optional.of(appHistory));

        // when
        volAppService.authorizeApplicant(1L, IsAuthorized.APPROVAL);

        // then
        assertThat(appHistory.getIsAuthorized()).isEqualTo(IsAuthorized.APPROVAL);
    }

    @Test
    void 봉사_거절() throws Exception {
        // given
        AppHistory appHistory = AppHistory.builder()
                .id(1L)
                .member(null)
                .comment(null)
                .privacyApproval(null)
                .volActivitySession(null)
                .isAuthorized(IsAuthorized.WAITING)
                .build();

        when(volAppRepository.findById(1L))
                .thenReturn(Optional.of(appHistory));

        // when
        volAppService.authorizeApplicant(1L, IsAuthorized.DISAPPROVAL);

        // then
        assertThat(appHistory.getIsAuthorized()).isEqualTo(IsAuthorized.DISAPPROVAL);
    }

    @Test
    void 봉사_승인_혹은_거절_취소() throws Exception {
        // given
        AppHistory appHistory = AppHistory.builder()
                .id(1L)
                .member(null)
                .comment(null)
                .privacyApproval(null)
                .volActivitySession(null)
                .isAuthorized(IsAuthorized.APPROVAL)
                .build();

        when(volAppRepository.findById(1L))
                .thenReturn(Optional.of(appHistory));

        // when
        volAppService.authorizeApplicant(1L, IsAuthorized.WAITING);

        // then
        assertThat(appHistory.getIsAuthorized()).isEqualTo(IsAuthorized.WAITING);
    }

    private ApplicationForm getApplicationForm() throws JsonProcessingException {
        String volAppFormString = "{ \"memberId\" : 100, \"comment\" : \"열심히 하겠습니다!\", \"privacyApproval\" : \"AGREE\" }";
        ApplicationForm applicationForm = objectMapper.readValue(volAppFormString, ApplicationForm.class);
        return applicationForm;
    }
}