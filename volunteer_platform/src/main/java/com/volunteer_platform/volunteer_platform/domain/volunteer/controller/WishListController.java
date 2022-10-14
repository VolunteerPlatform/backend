package com.volunteer_platform.volunteer_platform.domain.volunteer.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.form.WishListForm;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.MessageResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/wish-list")
public class WishListController {

    private final WishListService wishListService;
    private final JwtTokenService jwtTokenService;
    private final HttpServletRequest request;

    @PutMapping()
    public MessageResponse addWishList(@RequestBody WishListForm wishListForm) {
        wishListService.addWishList(getMemberId(), wishListForm.getSessionId());

        return MessageResponse.defaultOkayResponse();
    }

    @GetMapping
    public DTOResponse<List<SearchResultDto>> getWishList() {
        return new DTOResponse<>(wishListService.getWishList(getMemberId()));
    }


    private Long getMemberId() {
        return jwtTokenService.tokenToUserId(request);
    }
}
