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
            String username = memberService.findUsername(memberInfo.get());

            return new DTOResponse(username);
        } else {
            throw new IllegalStateException("존재 하지 않는 회원입니다.");
        }
    }

    @PostMapping("password-inquiry")
    public MessageResponse findMemberPassword(@RequestBody FindPasswordForm passwordForm) {
        memberService.editPassword(passwordForm);

        return MessageResponse.defaultOkayResponse();
    }

    @PostMapping("/password")
    public MessageResponse editPassword(@RequestBody EditPasswordForm passwordForm) {
        memberService.updatePassword(passwordForm);

        return MessageResponse.defaultOkayResponse();
    }
}
