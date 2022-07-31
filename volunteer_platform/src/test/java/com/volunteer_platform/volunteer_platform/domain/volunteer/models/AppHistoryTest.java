package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AppHistoryTest {

    @Test
    void 승인_처리() throws Exception {
        // given
        AppHistory appHistory = AppHistory.builder()
                .id(1L)
                .member(null)
                .comment(null)
                .privacyApproval(null)
                .volActivityTime(null)
                .isAuthorized(IsAuthorized.WAITING)
                .build();

        // when
        appHistory.setIsAuthorized(IsAuthorized.APPROVAL);

        // then
        assertThat(appHistory.getIsAuthorized())
                .isEqualTo(IsAuthorized.APPROVAL);
    }

    @Test
    void 거절_처리() throws Exception {
        // given
        AppHistory appHistory = AppHistory.builder()
                .id(1L)
                .member(null)
                .comment(null)
                .privacyApproval(null)
                .volActivityTime(null)
                .isAuthorized(IsAuthorized.WAITING)
                .build();

        // when
        appHistory.setIsAuthorized(IsAuthorized.DISAPPROVAL);

        // then
        assertThat(appHistory.getIsAuthorized())
                .isEqualTo(IsAuthorized.DISAPPROVAL);
    }

    @Test
    void 대기_처리() throws Exception {
        // given
        AppHistory appHistory = AppHistory.builder()
                .id(1L)
                .member(null)
                .comment(null)
                .privacyApproval(null)
                .volActivityTime(null)
                .isAuthorized(IsAuthorized.APPROVAL)
                .build();

        // when
        appHistory.setIsAuthorized(IsAuthorized.WAITING);

        // then
        assertThat(appHistory.getIsAuthorized())
                .isEqualTo(IsAuthorized.WAITING);
    }

    @Test
    void 완료_처리() throws Exception {
        // given
        AppHistory appHistory = AppHistory.builder()
                .id(1L)
                .member(null)
                .comment(null)
                .privacyApproval(null)
                .volActivityTime(null)
                .isAuthorized(IsAuthorized.APPROVAL)
                .build();

        // when
        appHistory.setIsAuthorized(IsAuthorized.COMPLETE);

        // then
        assertThat(appHistory.getIsAuthorized())
                .isEqualTo(IsAuthorized.COMPLETE);
    }
}