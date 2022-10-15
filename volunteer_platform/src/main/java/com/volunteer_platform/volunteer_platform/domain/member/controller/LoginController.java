package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberIdDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;
import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.MessageResponse;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final JwtTokenService jwtTokenService;

    // 센터측 회원가입
    @PostMapping("/center/signup")
    public MessageResponse centerSignup(@RequestBody CenterForm centerForm) {
        memberService.centerSignUp(centerForm);

        return MessageResponse.defaultOkayResponse();
    }


    // 센터측 로그인
    @PostMapping("/center/login")
    public MessageResponse centerLogin(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        memberService.memberLogin(loginForm, response);

        return MessageResponse.defaultOkayResponse();
    }


    // 회원 자체 회원가입
    @PostMapping("/members/signup")
    public MessageResponse signup(@RequestBody MemberForm memberForm) {
        Long memberId = memberService.memberSignUp(memberForm);

        return MessageResponse.defaultOkayResponse();
    }


    // 회원 자체 로그인
    @PostMapping("/members/login")
    public MessageResponse login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        memberService.memberLogin(loginForm, response);

        return MessageResponse.defaultOkayResponse();
    }

    // 아이디 중복검사
    @PostMapping("/members/validation/loginId")
    public MessageResponse loginIdValidation(@RequestBody LoginForm loginForm) {
        memberService.loginIdValidation(loginForm);

        return MessageResponse.defaultOkayResponse();
    }

    @GetMapping("/center/auth")
    public MessageResponse refreshAccessToken() {
        memberService.refreshAccessToken();

        return MessageResponse.defaultOkayResponse();
    }

    @PostMapping("/center/logout")
    public MessageResponse centerLogout() {
        memberService.centerLogout();

        return MessageResponse.defaultOkayResponse();
    }

    @GetMapping("/center/auth-check")
    public DTOResponse<MemberIdDto> validAccessToken(HttpServletRequest request) {
        Long userId = jwtTokenService.tokenToUserId(request);

        return new DTOResponse<>(new MemberIdDto(userId));
    }
}
