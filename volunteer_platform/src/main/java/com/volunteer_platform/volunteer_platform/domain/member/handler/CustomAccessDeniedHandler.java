package com.volunteer_platform.volunteer_platform.domain.member.handler;

import com.volunteer_platform.volunteer_platform.domain.member.models.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (request != null) {
            if (request.isUserInRole("ROLE_" + MemberRole.ROLE_MEMBER.name())) {
                this.redirectStrategy.sendRedirect(request, response, "/members/deny");
                return;
            }
        }

        if (request != null) {
            if (request.isUserInRole("ROLE_" + MemberRole.ROLE_PREMEMBER.name())) {
                this.redirectStrategy.sendRedirect(request, response, "pre-members/deny");
            }
        }

        this.redirectStrategy.sendRedirect(request, response, "/members/login");
    }
}
