package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberProfileController {

    private final MemberService memberService;
    private final JwtTokenService jwtTokenService;

    // 회원 프로플 가져오기
    @GetMapping("profile")
    public DTOResponse getProfileTest(HttpServletRequest request) {
        return memberService.getMemberProfile(getMemberId(request));
    }

    // 회원 프로필 - 비밀번호 인증
    @PostMapping("password-certification")
    public String certification(HttpServletRequest request, @RequestBody CertificationDto certificationDto) {
        return memberService.memberCertification(getMemberId(request), certificationDto);
    }

    // 회원 개인정보 수정
    @PutMapping("profile")
    public void editMemberProfile(HttpServletRequest request, @RequestBody MemberProfileUpdateDto memberProfileUpdateDto) {
        memberService.updateMember(getMemberId(request), memberProfileUpdateDto);
    }

    // 회원 비밀번호 수정
    @PutMapping("password")
    public void updateMemberPwd(HttpServletRequest request, @RequestBody MemberPwdUpdateDto memberPwdUpdateDto) {
        memberService.updateMemberPwd(getMemberId(request), memberPwdUpdateDto);
    }

    //회원 탈퇴
    @DeleteMapping("")
    public void withdrawal(HttpServletRequest request, @RequestBody WithdrawalForm withdrawalForm) {
        memberService.memberWithdrawal(getMemberId(request), withdrawalForm);
    }

    private Long getMemberId(HttpServletRequest request) {
        return jwtTokenService.tokenToUserId(request);
    }
}
