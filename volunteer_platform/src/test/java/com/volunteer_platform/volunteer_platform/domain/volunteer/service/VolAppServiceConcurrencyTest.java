package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolActivitySession;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VolAppServiceConcurrencyTest {

    private static final int NUMBER_OF_THREAD = 35;
    private static final Long SESSION_ID = 8L;
    private static final ExecutorService service = Executors.newFixedThreadPool(NUMBER_OF_THREAD);
    @Autowired
    private VolActivitySessionRepository activitySessionRepository;

    @Test
    void 지원자수_동시성_테스트() throws InterruptedException {
        // 세션 ID 를 가져와서, 그 세션에 대해 NUMBER_OF_THREAD 만큼의 쓰레드를 이용해 횟수 증가
        CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREAD);
        for (int i = 1; i <= NUMBER_OF_THREAD; i++) {
            service.execute(() -> {
                activitySessionRepository.increaseNumOfApplicant(SESSION_ID);
                latch.countDown();
            });
        }

        latch.await();

        // 실행한 횟수만큼 동일한지 확인
        VolActivitySession findSession = activitySessionRepository.findById(SESSION_ID).get();
        assertThat(findSession.getNumOfApplicant()).isEqualTo(NUMBER_OF_THREAD);
    }
}
