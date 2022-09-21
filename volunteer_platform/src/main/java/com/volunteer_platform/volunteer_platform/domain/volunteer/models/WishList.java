package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class WishList {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private Long sessionId;
}
