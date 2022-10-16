package com.volunteer_platform.volunteer_platform.domain.member.repository;

import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long> {

    @Query("select mi from MemberInfo mi where mi.phoneNumber = :phoneNumber and mi.userRealName = :userRealName")
    Optional<MemberInfo> validInfo(@Param("phoneNumber") String phoneNumber, @Param("userRealName") String userRealName);

    @Query("select mi.id from MemberInfo mi where mi.phoneNumber = :phoneNumber and mi.userRealName = :userRealName")
    Optional<Long> getMemberId(@Param("phoneNumber") String phoneNumber, @Param("userRealName") String userRealName);
}
