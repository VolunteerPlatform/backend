package com.volunteer_platform.volunteer_platform.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        //유효한 토큰인가?
        if (accessToken != null) {
            // AccessToken 이 유효하면
            if (jwtTokenProvider.validateToken(accessToken)) {
                this.setAuthentication(accessToken);
            }

            // AccessToken 은 만료, Refresh 은 존재
            else if (!jwtTokenProvider.validateToken(accessToken) && refreshToken != null) {
                // RefreshToken 유효?
                boolean validRefreshToken = jwtTokenProvider.validateToken(refreshToken);

                // RefreshToken DB 에 존재?
                boolean isRefreshToken = jwtTokenProvider.existsRefreshToken(refreshToken);

                if (validRefreshToken && isRefreshToken) {
                    String userEmail = jwtTokenProvider.getUserEmail(refreshToken);
                    List<String> roles = jwtTokenProvider.getRoles(userEmail);
                    String newAccessToken = jwtTokenProvider.createAccessToken(userEmail, roles);
                    jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                    this.setAuthentication(newAccessToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}