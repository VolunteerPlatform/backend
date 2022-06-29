package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Period {
    private LocalDate begin;
    private LocalDate end;

    public Period(String begin, String end) {
        this.begin = LocalDate.parse(begin);
        this.end = LocalDate.parse(end);

        if (this.begin.compareTo(this.end) > 0) {
            throw new IllegalArgumentException("시작일은 종료일보다 이후일 수 없습니다.");
        }
    }
}
