package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.enumtype.IsAuthorized;
import lombok.Getter;

@Getter
public class AuthorizeForm {
    private IsAuthorized isAuthorized;
}
