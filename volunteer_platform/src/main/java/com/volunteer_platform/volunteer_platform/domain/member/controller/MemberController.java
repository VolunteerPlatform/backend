package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberInfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberInfoService memberInfoService;


    // 회원 아이디 찾기
    @PostMapping("id-inquiry")
    public String findMemberId(@RequestBody FindForm findForm) {
        Optional<MemberInfo> memberInfo = memberInfoService.validMemberInfo(findForm);

        if (memberInfo.isEmpty()) {
            return "redirect:/find/memberInfo";
        } else {
            return memberService.findUsername(memberInfo.get());
        }
    }
}
