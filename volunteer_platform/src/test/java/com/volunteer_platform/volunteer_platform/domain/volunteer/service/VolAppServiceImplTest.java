package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.AppHistoryDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.ApplicationForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.AppHistory;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivity;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.AuthorizationType;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.SessionStatus;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolAppRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
class VolAppServiceImplTest {

    @Mock
    VolActivitySessionRepository volActivitySessionRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private VolAppRepository volAppRepository;

    @InjectMocks
    private VolAppServiceImpl volAppService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("사용자 신청 승인/거절/승인거절 철회")
    class AuthorizeApplicant {
        @Test
        void 봉사_승인() throws Exception {
            // given
            VolOrgan volOrgan = VolOrgan.builder()
                    .id(103L)
                    .name("안성시자원봉사센터")
                    .build();

            VolActivity volActivity = VolActivity.builder()
                    .id(101L)
                    .volOrgan(volOrgan)
                    .build();

            VolActivitySession session = VolActivitySession.builder()
                    .id(100L)
                    .volActivity(volActivity)
                    .build();

            Member member = Member.builder()
                    .id(1L)
                    .build();

            AppHistory appHistory = AppHistory.builder()
                    .id(1L)
                    .member(member)
                    .comment(null)
                    .privacyApproval(null)
                    .volActivitySession(session)
                    .isAuthorized(IsAuthorized.WAITING)
                    .build();

            when(volAppRepository.findById(any()))
                    .thenReturn(Optional.of(appHistory));

            // when
            AppHistoryDto appHistoryDto = volAppService.authorizeApplicant(1L, IsAuthorized.APPROVAL);

            // then
            assertThat(appHistory.getIsAuthorized()).isEqualTo(IsAuthorized.APPROVAL);
        }

        @Test
        void 봉사_거절() throws Exception {
            // given
            VolOrgan volOrgan = VolOrgan.builder()
                    .id(103L)
                    .name("안성시자원봉사센터")
                    .build();

            VolActivity volActivity = VolActivity.builder()
                    .id(101L)
                    .volOrgan(volOrgan)
                    .build();

            VolActivitySession session = VolActivitySession.builder()
                    .id(100L)
                    .volActivity(volActivity)
                    .build();

            Member member = Member.builder()
                    .id(1L)
                    .build();

            AppHistory appHistory = AppHistory.builder()
                    .id(1L)
                    .member(member)
                    .comment(null)
                    .privacyApproval(null)
                    .volActivitySession(session)
                    .isAuthorized(IsAuthorized.WAITING)
                    .build();


            when(volAppRepository.findById(any()))
                    .thenReturn(Optional.of(appHistory));

            // when
            volAppService.authorizeApplicant(1L, IsAuthorized.DISAPPROVAL);

            // then
            assertThat(appHistory.getIsAuthorized()).isEqualTo(IsAuthorized.DISAPPROVAL);
        }

        @Test
        void 봉사_승인_취소() throws Exception {
            // given
            VolOrgan volOrgan = VolOrgan.builder()
                    .id(103L)
                    .name("안성시자원봉사센터")
                    .build();

            VolActivity volActivity = VolActivity.builder()
                    .id(101L)
                    .volOrgan(volOrgan)
                    .build();

            VolActivitySession session = VolActivitySession.builder()
                    .id(100L)
                    .volActivity(volActivity)
                    .build();

            Member member = Member.builder()
                    .id(1L)
                    .build();

            AppHistory appHistory = AppHistory.builder()
                    .id(1L)
                    .member(member)
                    .comment(null)
                    .privacyApproval(null)
                    .volActivitySession(session)
                    .isAuthorized(IsAuthorized.APPROVAL)
                    .build();

            when(volAppRepository.findById(any()))
                    .thenReturn(Optional.of(appHistory));

            // when
            volAppService.authorizeApplicant(1L, IsAuthorized.WAITING);

            // then
            assertThat(appHistory.getIsAuthorized()).isEqualTo(IsAuthorized.WAITING);
        }

        @Test
        void 봉사_거절_취소는_불가능() {
            // given
            VolOrgan volOrgan = VolOrgan.builder()
                    .id(103L)
                    .name("안성시자원봉사센터")
                    .build();

            VolActivity volActivity = VolActivity.builder()
                    .id(101L)
                    .volOrgan(volOrgan)
                    .build();

            VolActivitySession session = VolActivitySession.builder()
                    .id(100L)
                    .volActivity(volActivity)
                    .build();

            Member member = Member.builder()
                    .id(1L)
                    .build();

            AppHistory appHistory = AppHistory.builder()
                    .id(1L)
                    .member(member)
                    .comment(null)
                    .privacyApproval(null)
                    .volActivitySession(session)
                    .isAuthorized(IsAuthorized.DISAPPROVAL)
                    .build();

            when(volAppRepository.findById(1L))
                    .thenReturn(Optional.of(appHistory));

            // when, then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> {
                        volAppService.authorizeApplicant(1L, IsAuthorized.WAITING);
                    }).withMessage("미승인된 지원자의 상태는 변경할 수 없습니다.");

        }
    }

    @Nested
    @DisplayName("봉사 지원")
    class ApplyActivity {
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

            VolOrgan volOrgan = VolOrgan.builder()
                    .id(101L)
                    .name("안성시자원봉사센터")
                    .build();

            VolActivity volActivity = VolActivity.builder()
                    .volOrgan(volOrgan)
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
            AppHistoryDto appHistoryDto = volAppService.volApply(101L, applicationForm);

            // then
            assertThat(appHistoryDto.getMemberId()).isEqualTo(member.getId());
            assertThat(appHistoryDto.getActivityDate()).isEqualTo(volActivitySession.getActivityDate());
            assertThat(appHistoryDto.getStartTime()).isEqualTo(volActivitySession.getStartTime());
            assertThat(appHistoryDto.getEndTime()).isEqualTo(volActivitySession.getEndTime());
            assertThat(appHistoryDto.getIsAuthorized()).isEqualTo(IsAuthorized.WAITING);
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
        void 자동승인_봉사는_신청즉시_승인처리() throws Exception {
            // given
            ApplicationForm applicationForm = getApplicationForm();

            Member member = Member.builder()
                    .id(100L)
                    .userName("HAN")
                    .password("1234")
                    .build();

            VolOrgan volOrgan = VolOrgan.builder()
                    .id(101L)
                    .name("안성시자원봉사센터")
                    .build();

            VolActivity volActivity = VolActivity.builder()
                    .authorizationType(AuthorizationType.UNNECESSARY)
                    .volOrgan(volOrgan)
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
            AppHistoryDto appHistoryDto = volAppService.volApply(101L, applicationForm);

            // then
            assertThat(appHistoryDto.getIsAuthorized()).isEqualTo(IsAuthorized.APPROVAL);
        }
    }


    @Nested
    @DisplayName("봉사 신청 취소")
    class CancelApplication {
        @Test
        void 봉사_취소_정상처리() {
            // given
            VolActivity volActivity = VolActivity.builder()
                    .authorizationType(AuthorizationType.UNNECESSARY)
                    .build();

            VolActivitySession volActivitySession = VolActivitySession.builder()
                    .id(101L)
                    .volActivity(volActivity)
                    .activityDate(LocalDate.now().plusDays(10))
                    .startTime(13)
                    .endTime(15)
                    .numOfRecruit(30)
                    .numOfApplicant(15)
                    .activityWeek(DayOfWeek.SUNDAY)
                    .sessionStatus(SessionStatus.RECRUITING)
                    .build();

            AppHistory appHistory = AppHistory.builder()
                    .isAuthorized(IsAuthorized.WAITING)
                    .volActivitySession(volActivitySession)
                    .build();

            when(volAppRepository.findById(any()))
                    .thenReturn(Optional.of(appHistory));

            // when
            volAppService.cancelApplication(101L);

            // then
            verify(volAppRepository).deleteById(any());
        }

        @Test
        void 봉사_취소_활동완료시_취소불가() {
            // given
            AppHistory appHistory = AppHistory.builder()
                    .isAuthorized(IsAuthorized.COMPLETE)
                    .build();

            when(volAppRepository.findById(any()))
                    .thenReturn(Optional.of(appHistory));

            // when, then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> {
                        volAppService.cancelApplication(101L);
                    })
                    .withMessage("활동이 완료된 활동은 취소가 불가능합니다.");
        }

        @Test
        void 승인활동_취소_활동시작일_하루전_취소실패() {
            // given
            VolActivitySession volActivitySession = VolActivitySession.builder()
                    .id(101L)
                    .volActivity(null)
                    .activityDate(LocalDate.now().plusDays(1))
                    .startTime(13)
                    .endTime(15)
                    .numOfRecruit(30)
                    .numOfApplicant(15)
                    .activityWeek(DayOfWeek.SUNDAY)
                    .sessionStatus(SessionStatus.RECRUITING)
                    .build();

            AppHistory appHistory = AppHistory.builder()
                    .isAuthorized(IsAuthorized.APPROVAL)
                    .volActivitySession(volActivitySession)
                    .build();

            when(volAppRepository.findById(any()))
                    .thenReturn(Optional.of(appHistory));

            // when, then
            assertThatIllegalArgumentException()
                    .isThrownBy(() -> {
                        volAppService.cancelApplication(101L);
                    }).withMessageStartingWith("봉사활동은 활동 시작일 기준 ");
        }

        @Test
        void 승인활동_취소_기준일이전_취소가능() {
            // given
            VolActivitySession volActivitySession = VolActivitySession.builder()
                    .id(101L)
                    .volActivity(null)
                    .activityDate(LocalDate.now().plusDays(10))
                    .startTime(13)
                    .endTime(15)
                    .numOfRecruit(30)
                    .numOfApplicant(15)
                    .activityWeek(DayOfWeek.SUNDAY)
                    .sessionStatus(SessionStatus.RECRUITING)
                    .build();

            AppHistory appHistory = AppHistory.builder()
                    .id(100L)
                    .isAuthorized(IsAuthorized.APPROVAL)
                    .volActivitySession(volActivitySession)
                    .build();

            when(volAppRepository.findById(any()))
                    .thenReturn(Optional.of(appHistory));

            // when
            volAppService.cancelApplication(100L);

            // then
            verify(volAppRepository).deleteById(any());

        }
    }

    private ApplicationForm getApplicationForm() throws JsonProcessingException {
        String volAppFormString = "{ \"memberId\" : 100, \"comment\" : \"열심히 하겠습니다!\", \"privacyApproval\" : \"AGREE\" }";
        ApplicationForm applicationForm = objectMapper.readValue(volAppFormString, ApplicationForm.class);
        return applicationForm;
    }
}