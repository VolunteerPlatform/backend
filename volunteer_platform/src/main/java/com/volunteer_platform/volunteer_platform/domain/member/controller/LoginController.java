package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member1365Info;
import com.volunteer_platform.volunteer_platform.domain.member.service.Member1365InfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.MemberInfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final Member1365InfoService member1365InfoService;
    private final MemberInfoService memberInfoService;

    @GetMapping("/api/user/test")
    public String getInfo() {
        return "TOKEN OK";
    }

    @PostMapping("/api/signup")
    public Long signup(@RequestBody MemberForm memberForm) {
        return memberService.MemberSignUp(memberForm);
    }

    @PostMapping("/api/signup/info")
    public Long signupInfo(@RequestBody MemberForm memberForm, HttpServletRequest request) {
        return memberInfoService.createMemberInfo(memberForm, request);
    }

    @PostMapping("/api/signup/1365")
    public Long signup1365(@RequestBody MemberForm memberForm, HttpServletRequest request) {
        return member1365InfoService.createMember1365Info(memberForm, request);
    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.MemberLogin(loginForm, response);
    }
}
