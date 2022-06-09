package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;


    // 회원 프로플 가져오기
    @GetMapping("/api/member/profile")
    public MemberDto getProfileTest(HttpServletRequest request) {
        Member memberId = memberService.findMemberId(request);

        return memberRepository.findMemberProfileCustom(memberId.getId());
    }

    // 회원 개인정보 수정
    @PutMapping("/api/member/profile/edit")
    public void editMemberProfile(HttpServletRequest request, @RequestBody MemberProfileUpdateDto memberProfileUpdateDto) {
        memberService.updateMember(request, memberProfileUpdateDto);
    }

    // 회원  비밀번호 수정
    @PutMapping("/api/member/profile/edit/pwd")
    public void updateMemberPwd(HttpServletRequest request, @RequestBody MemberPwdUpdateDto memberPwdUpdateDto) {
        memberService.updateMemberPwd(request, memberPwdUpdateDto);
    }
}
