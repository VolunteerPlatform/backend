package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member1365Info;
import com.volunteer_platform.volunteer_platform.domain.member.repository.Member1365InfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class Member1365InfoService {

    private final Member1365InfoRepository member1365InfoRepository;
    private final MemberService memberService;


    @Transactional
    public void saveMember1365Info(Member1365Info member1365Info) {
        member1365InfoRepository.save(member1365Info);
    }

    @Transactional
    public Long createMember1365Info(MemberForm memberForm, HttpServletRequest request) {
        Member memberId = memberService.findMemberId(request);

        Member1365Info member1365Info = Member1365Info.builder()
                .centerName(memberForm.getCenterName())
                .idOf1365(memberForm.getIdOf1365())
                .member(memberId)
                .build();

        member1365InfoRepository.save(member1365Info);

        return memberId.getId();
    }
}
