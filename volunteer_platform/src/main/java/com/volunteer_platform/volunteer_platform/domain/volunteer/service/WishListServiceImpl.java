package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.WishList;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.WishListRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.*;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    @Override
    @Transactional
    public DTOResponse addWishList(Long memberId, Long sessionId) {

        Optional<WishList> wishList = wishListRepository.findWishList(memberId, sessionId);

        wishList.ifPresent(wishListRepository::delete);

        WishList newWishList = WishList.builder()
                .memberId(memberId)
                .sessionId(sessionId)
                .build();

        wishListRepository.save(newWishList);

        String message = "성공적으로 찜하기되었습니다.";

        return new DTOResponse(HttpStatus.CREATED.value(), message, message);
    }
}
