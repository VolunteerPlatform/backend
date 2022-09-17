package com.volunteer_platform.volunteer_platform.config.jwt;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService{

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    // AccessToken 으로 userName(아이디) 흭득
    @Override
    public String tokenToUserName(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        return jwtTokenProvider.getUserName(accessToken);
    }

    // AccessToken 으로 userId(PK) 흭득
    @Override
    public Long tokenToUserId(HttpServletRequest request) {
        String userName = tokenToUserName(request);
        Member member = memberRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
        return member.getId();
    }
}
