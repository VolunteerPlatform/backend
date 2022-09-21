package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    // 센터측 회원가입
    @PostMapping("/center/signup")
    public DTOResponse centerSignup(@RequestBody CenterForm centerForm) {
        return memberService.centerSignUp(centerForm);
    }


    // 센터측 로그인
    @PostMapping("/center/login")
    public DTOResponse centerLogin(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.memberLogin(loginForm, response);
    }


    // 회원 자체 회원가입
    @PostMapping("/members/signup")
    public DTOResponse signup(@RequestBody MemberForm memberForm) {
        Long memberId = memberService.memberSignUp(memberForm);

        return new DTOResponse(memberId);
    }


    // 회원 자체 로그인
    @PostMapping("/members/login")
    public DTOResponse login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.memberLogin(loginForm, response);
    }

    // 아이디 중복검사
    @PostMapping("/members/validation/loginId")
    public DTOResponse loginIdValidation(@RequestBody LoginForm loginForm) {
        return memberService.loginIdValidation(loginForm);
    }
}
