package com.volunteer_platform.volunteer_platform.entity.register;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
