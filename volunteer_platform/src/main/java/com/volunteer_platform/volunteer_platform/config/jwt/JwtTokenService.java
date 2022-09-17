package com.volunteer_platform.volunteer_platform.config.jwt;

import javax.servlet.http.HttpServletRequest;

public interface JwtTokenService {

    String tokenToUserName(HttpServletRequest request);

    Long tokenToUserId(HttpServletRequest request);
}
