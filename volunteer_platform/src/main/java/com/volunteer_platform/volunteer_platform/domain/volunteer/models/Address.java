package com.volunteer_platform.volunteer_platform.domain.volunteer.models;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Address {

    private String detailAddress;
    private String zipcode;
}
