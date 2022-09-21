package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse;

public interface WishListService {
    CustomResponse.DTOResponse addWishList(Long memberId, Long sessionId);
}
