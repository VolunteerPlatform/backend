package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Period {

    private LocalDate begin;
    private LocalDate end;
}
