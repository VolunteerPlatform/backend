package com.volunteer_platform.volunteer_platform.domain.member.controller;

import com.volunteer_platform.volunteer_platform.domain.member.form.EditPasswordForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.FindPasswordForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberInfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberInfoService memberInfoService;


    // 회원 아이디 찾기
    @PostMapping("id-inquiry")
    public DTOResponse findMemberId(@RequestBody FindForm findForm) {
        Optional<MemberInfo> memberInfo = memberInfoService.validMemberInfo(findForm);

        if (memberInfo.isPresent()) {
            return memberService.findUsername(memberInfo.get());
        }

        return new DTOResponse(HttpStatus.BAD_REQUEST.value(), "fail", "fail");
    }

    @PostMapping("password-inquiry")
    public DTOResponse findMemberPassword(@RequestBody FindPasswordForm passwordForm) {
        return memberService.editPassword(passwordForm);
    }

    @PostMapping("/password")
    public DTOResponse editPassword(@RequestBody EditPasswordForm passwordForm) {
        return memberService.updatePassword(passwordForm);
    }
}
