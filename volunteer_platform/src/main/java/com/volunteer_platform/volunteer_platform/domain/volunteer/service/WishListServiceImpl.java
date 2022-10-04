package com.volunteer_platform.volunteer_platform.domain.volunteer.service;

import com.volunteer_platform.volunteer_platform.domain.volunteer.controller.dto.SearchResultDto;
import com.volunteer_platform.volunteer_platform.domain.volunteer.models.WishList;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.VolActivitySessionRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.repository.WishListRepository;
import com.volunteer_platform.volunteer_platform.domain.volunteer.service.volinterface.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final VolActivitySessionRepository volActivitySessionRepository;

    @Override
    @Transactional
    public void addWishList(Long memberId, Long sessionId) {
        isExistSession(sessionId);

        Optional<WishList> wishList = wishListRepository.findWishList(memberId, sessionId);
        if (wishList.isPresent()) {
            wishListRepository.deleteById(wishList.get().getId());
        } else {
            WishList newWishList = WishList.builder()
                    .memberId(memberId)
                    .sessionId(sessionId)
                    .build();

            wishListRepository.save(newWishList);
        }
    }

    @Override
    public List<SearchResultDto> getWishList(Long memberId) {
        List<Long> wishedSession = wishListRepository.findByMemberId(memberId).stream()
                .map(o -> o.getSessionId())
                .collect(Collectors.toList());

        return volActivitySessionRepository.findAllById(wishedSession)
                .stream()
                .map(SearchResultDto::of)
                .collect(Collectors.toList());
    }

    private void isExistSession(Long sessionId) {
        if (!volActivitySessionRepository.existsById(sessionId)) {
            throw new IllegalArgumentException("존재하지 않는 세션 ID 입니다.");
        }
    }
}
