package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenProvider;
import com.volunteer_platform.volunteer_platform.domain.member.auth.RefreshToken;
import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.member.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
    private final MemberInfoService memberInfoService;
    private final Member1365InfoService member1365InfoService;

    public Long MemberSignUp(MemberForm memberForm) {
        boolean pass = memberValidation(memberForm.getUserName()); // 아이디 중복 검사

        if (pass) {
            Long memberId = memberRepository.save(Member.builder()
                    .userName(memberForm.getUserName())
                    .password(passwordEncoder.encode(memberForm.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER")) // 일반 유저
                    .googleId(null)
                    .kakaoId(null)
                    .build()).getId();

            Optional<Member> findMemberIdForMember = memberRepository.findMemberId(memberId);

            memberInfoService.createMemberInfo(memberForm, findMemberIdForMember);
            member1365InfoService.createMember1365Info(memberForm, findMemberIdForMember);

            return memberId;
        } else throw new IllegalStateException("아이디가 중복되었습니다.");
    }


    public String MemberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.findByUserName(loginForm.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        tokenRepository.save(new RefreshToken(refreshToken));

        return member.getUsername();
    }

    public boolean memberValidation(String userName) {
        Optional<Member> member = memberRepository.findByUserName(userName);

        if (member.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Member findMemberId(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        String userName = jwtTokenProvider.getUserName(token);
        Optional<Member> userId = memberRepository.findByUserName(userName);

        if (userId.isPresent()) {
            return userId.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}
