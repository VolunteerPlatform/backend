package com.volunteer_platform.volunteer_platform.entity.register;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Period {

    private LocalDateTime begin;
    private LocalDateTime end;
}
