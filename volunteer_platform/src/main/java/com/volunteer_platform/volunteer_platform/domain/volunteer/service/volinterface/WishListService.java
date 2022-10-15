package com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;

import java.util.List;

public interface WishListService {
    void addWishList(Long memberId, Long sessionId);

    List<SearchResultDto> getWishList(Long memberId);
}
