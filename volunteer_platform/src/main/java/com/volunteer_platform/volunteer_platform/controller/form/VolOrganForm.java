package com.volunteer_platform.volunteer_platform.controller.form;

import com.volunteer_platform.volunteer_platform.entity.register.Address;
import lombok.Getter;

@Getter
public class VolOrganForm {

    private String manager;
    private String organPhoneNumber;
    private Address address;
}
