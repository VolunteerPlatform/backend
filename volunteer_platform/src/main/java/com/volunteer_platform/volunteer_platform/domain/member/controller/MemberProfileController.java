package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/api/member-profile")
    public MemberDto getProfileTest(HttpServletRequest request) {
        Member memberId = memberService.findMemberId(request);

        return memberRepository.findMemberProfileCustom(memberId.getId());
    }
}
