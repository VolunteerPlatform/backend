package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.Address;
import lombok.Getter;

@Getter
public class OrganizationModifyForm {
    private String name;
    private String manager;
    private String contact;
    private Address address;
}
