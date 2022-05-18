package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import lombok.Getter;

import java.util.List;

@Getter
public class Form {

    private VolActivityForm volActivityForm;
    private VolOrganForm volOrganForm;
    private List<VolActivityTimeForm> volActivityTimeForms;
}
