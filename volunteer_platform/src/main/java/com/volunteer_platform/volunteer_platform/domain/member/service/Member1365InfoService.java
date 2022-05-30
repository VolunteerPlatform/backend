package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member1365Info;
import com.volunteer_platform.volunteer_platform.domain.member.repository.Member1365InfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Member1365InfoService {

    private final Member1365InfoRepository member1365InfoRepository;


    @Transactional
    public void saveMember1365Info(Member1365Info member1365Info) {
        member1365InfoRepository.save(member1365Info);
    }

    @Transactional
    public void createMember1365Info(MemberForm memberForm, Optional<Member> member) {

        Member1365Info member1365Info = Member1365Info.builder()
                .centerName(memberForm.getCenterName())
                .idOf1365(memberForm.getIdOf1365())
                .member(member.get())
                .build();

        member1365InfoRepository.save(member1365Info);

    }
}
