package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.controller.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member1365Info;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberInfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final MemberInfoService memberInfoService;

    // 회원 자체 회원가입
    @PostMapping("/members/signup")
    public Long signup(
            @RequestBody MemberForm memberForm,
            Member member,
            MemberInfo memberInfo,
            Member1365Info member1365Info
    ) {
        return memberService.memberSignUp(memberForm, member, memberInfo, member1365Info);
    }

    // 회원 자체 로그인
    @PostMapping("/api/login")
    public String login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        return memberService.memberLogin(loginForm, response);
    }

//    // 회원 프로필 - 비밀번호 인증
//    @PostMapping("/api/certification")
//    public String certification(HttpServletRequest request, @RequestBody CertificationDto certificationDto) {
//        return memberService.memberCertification(request, certificationDto);
//    }
//
//    // 센터측 회원가입
//    @PostMapping("/api/signup/center")
//    public Long centerSignup(@RequestBody CenterForm centerForm) {
//        return memberService.centerSignUp(centerForm);
//    }
//
//    // 센터측 로그인
//    @PostMapping("/api/login/center")
//    public String centerLogin(@RequestBody LoginForm loginForm, HttpServletResponse response) {
//        return memberService.memberLogin(loginForm, response);
//    }
//
//    // 회원 아이디 찾기
//    @PostMapping("/find/memberInfo")
//    public String findMemberId(@RequestBody FindForm findForm) {
//        Optional<MemberInfo> memberInfo = memberInfoService.validMemberInfo(findForm);
//
//        if (memberInfo.isEmpty()) {
//            return "redirect:/find/memberInfo";
//        } else {
//            return memberService.findUsername(memberInfo.get());
//        }
//    }
}
