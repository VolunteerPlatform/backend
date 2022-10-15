package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenService;
import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;
import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.MessageResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberProfileController {

    private final MemberService memberService;
    private final JwtTokenService jwtTokenService;
    private final HttpServletRequest request;

    // 회원 프로플 가져오기
    @GetMapping("profile")
    public DTOResponse getMemberProfile() {
        MemberDto memberProfile = memberService.getMemberProfile(getMemberId());

        return new DTOResponse(memberProfile);
    }

    // 회원 프로필 - 비밀번호 인증
    @PostMapping("password-certification")
    public MessageResponse certification(@RequestBody CertificationDto certificationDto) {
        memberService.memberCertification(getMemberId(), certificationDto);

        return MessageResponse.defaultOkayResponse();
    }

    // 회원 개인정보 수정
    @PostMapping("profile")
    public MessageResponse editMemberProfile(@RequestBody MemberProfileUpdateDto memberProfileUpdateDto) {
        memberService.updateMember(getMemberId(), memberProfileUpdateDto);

        return MessageResponse.defaultOkayResponse();
    }

    // 회원 비밀번호 수정
    @PutMapping("password")
    public MessageResponse updateMemberPwd(@RequestBody MemberPwdUpdateDto memberPwdUpdateDto) {
        memberService.updateMemberPwd(getMemberId(), memberPwdUpdateDto);

        return MessageResponse.defaultOkayResponse();
    }

    //회원 탈퇴
    @DeleteMapping("")
    public MessageResponse withdrawal(@RequestBody WithdrawalForm withdrawalForm) {
        memberService.memberWithdrawal(getMemberId(), withdrawalForm);

        return MessageResponse.defaultOkayResponse();
    }

    private Long getMemberId() {
        return jwtTokenService.tokenToUserId(request);
    }
}
