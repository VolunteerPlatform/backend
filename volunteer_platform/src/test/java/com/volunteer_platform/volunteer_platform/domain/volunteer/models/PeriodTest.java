package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PeriodTest {

    @Test
    void 시작일이_종료일_이전() throws Exception {
        String begin = "2022-05-25";
        String end = "2022-05-31";
        Period period = new Period(begin, end);

        Assertions.assertThat(period.getBegin()).isEqualTo(begin);
        Assertions.assertThat(period.getEnd()).isEqualTo(end);
    }

    @Test
    void 시작일과_종료일이_동일() throws Exception {
        String begin = "2022-05-25";
        String end = "2022-05-25";
        Period period = new Period(begin, end);

        Assertions.assertThat(period.getBegin()).isEqualTo(begin);
        Assertions.assertThat(period.getEnd()).isEqualTo(end);
    }

    @Test
    void 시작일이_종료일_이후() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            Period period = new Period("2022-06-01", "2021-06-01");
        });
    }
}
