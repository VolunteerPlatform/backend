package com.volunteer_platform.volunteer_platform.domain.volunteer.repository;

import com.volunteer_platform.volunteer_platform.domain.volunteer.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("select w from WishList w where w.memberId = :memberId and w.sessionId = :sessionId")
    Optional<WishList> findWishList(@Param("memberId") Long memberId, @Param("sessionId") Long sessionId);

    List<WishList> findByMemberId(Long memberId);
}
