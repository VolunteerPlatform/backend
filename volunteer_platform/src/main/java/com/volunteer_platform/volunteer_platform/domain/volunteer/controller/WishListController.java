package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.WishListForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/wish-list")
public class WishListController {

    private final WishListService wishListService;
    private final JwtTokenService jwtTokenService;

    @PutMapping()
    public DTOResponse addWishList(HttpServletRequest request, @RequestBody WishListForm wishListForm) {
        return wishListService.addWishList(getMemberId(request), wishListForm.getSessionId());
    }


    private Long getMemberId(HttpServletRequest request) {
        return jwtTokenService.tokenToUserId(request);
    }
}
