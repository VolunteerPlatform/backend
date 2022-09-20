package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member1365Info;
import com.volunteer_platform.volunteer_platform.domain.member.repository.Member1365InfoRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.Member1365InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Member1365InfoServiceImpl implements Member1365InfoService {

    private final Member1365InfoRepository member1365InfoRepository;

    @Override
    @Transactional
    public void createMember1365Info(MemberForm memberForm, Member member) {
        Member1365Info member1365Info = memberForm.toMember1365InfoEntity(member);

        member1365InfoRepository.save(member1365Info);
    }
}
