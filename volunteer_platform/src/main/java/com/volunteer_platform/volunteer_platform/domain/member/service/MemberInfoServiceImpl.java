package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.controller.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberInfoRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;

    @Override
    @Transactional
    public void createMemberInfo(MemberForm memberForm, Optional<Member> member, MemberInfo memberInfo) {
        MemberInfo memberInfoData = memberForm.toEntity(memberInfo, member.orElseThrow());

        memberInfoRepository.save(memberInfoData);
    }
}
