package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;
    private final MemberService memberService;

    @Transactional
    public void saveMemberInfo(MemberInfo memberInfo) {
        memberInfoRepository.save(memberInfo);
    }

    @Transactional
    public Long createMemberInfo(MemberForm memberForm, HttpServletRequest request) {
        Member memberId = memberService.findMemberId(request);

        MemberInfo memberInfo = MemberInfo.builder()
                .birthday(memberForm.getBirthday())
                .gender(memberForm.getGender())
                .phoneNumber(memberForm.getPhoneNumber())
                .userRealName(memberForm.getUserRealName())
                .member(memberId)
                .build();

        memberInfoRepository.save(memberInfo);

        return memberId.getId();
    }
}
