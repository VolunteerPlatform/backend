package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberInfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberInfoService memberInfoService;

    // 회원 프로필 - 비밀번호 인증
    @PostMapping("/members/certification")
    public String certification(HttpServletRequest request, @RequestBody CertificationDto certificationDto) {
        return memberService.memberCertification(request, certificationDto);
    }


    // 회원 아이디 찾기
    @PostMapping("/find/memberInfo")
    public String findMemberId(@RequestBody FindForm findForm) {
        Optional<MemberInfo> memberInfo = memberInfoService.validMemberInfo(findForm);

        if (memberInfo.isEmpty()) {
            return "redirect:/find/memberInfo";
        } else {
            return memberService.findUsername(memberInfo.get());
        }
    }
}
