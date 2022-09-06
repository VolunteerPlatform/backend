package com.volunteer_platform.volunteer_platform.domain.member.handler;

import com.volunteer_platform.volunteer_platform.domain.member.models.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        if (request != null) {
            if (request.isUserInRole("ROLE_" + MemberRole.MEMBER.name())) {
                this.redirectStrategy.sendRedirect(request, response, "/members/deny");
                return;
            }
        }

        if (request != null) {
            if (request.isUserInRole("ROLE_" + MemberRole.PREMEMBER.name())) {
                this.redirectStrategy.sendRedirect(request, response, "pre-members/deny");
            }
        }

        this.redirectStrategy.sendRedirect(request, response, "/members/login");
    }
}
