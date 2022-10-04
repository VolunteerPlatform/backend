package com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishListForm {
    private Long sessionId;
}
