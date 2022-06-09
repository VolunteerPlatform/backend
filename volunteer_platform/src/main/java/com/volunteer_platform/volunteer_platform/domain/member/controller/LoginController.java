package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    // 회원 자체 회원가입
    @PostMapping("/api/signup")
    public Long signup(@RequestBody MemberForm memberForm) {
        return memberService.MemberSignUp(memberForm);
    }

    // 회원 자체 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.MemberLogin(loginForm, response);
    }

    // 회원 프로필 - 비밀번호 인증
    @PostMapping("/api/certification")
    public String certification(HttpServletRequest request, @RequestBody CertificationDto certificationDto) {
        return memberService.MemberCertification(request, certificationDto);
    }

    // 센터측 회원가입
    @PostMapping("/api/signup/center")
    public Long centerSignup(@RequestBody CenterForm centerForm) {
        return memberService.CenterSignUp(centerForm);
    }

    // 센터측 로그인
    @PostMapping("/api/login/center")
    public String centerLogin(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.MemberLogin(loginForm, response);
    }
}
