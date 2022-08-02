package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Address;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.VolOrgan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolOrganDto {

    private Long id;

    private String name;

    private String manager;

    private String organPhoneNumber;

    private Address address;

    public static VolOrganDto of(VolOrgan volOrgan) {
        return VolOrganDto.builder()
                .id(volOrgan.getId())
                .name(volOrgan.getName())
                .manager(volOrgan.getManager())
                .organPhoneNumber(volOrgan.getContact())
                .address(volOrgan.getAddress())
                .build();
    }

}
