package com.volunteer_platform.volunteer_platform.domain.member.repository;

import com.volunteer_platform.volunteer_platform.domain.member.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token);
}
