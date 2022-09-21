package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
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
    public void createMemberInfo(MemberForm memberForm, Member member) {
        MemberInfo memberInfo = memberForm.toMemberInfoEntity(member);

        memberInfoRepository.save(memberInfo);
    }

    /**
     * 회원가입한 유저인지 확인
     * @param findForm
     * @return
     */
    @Override
    public Optional<MemberInfo> validMemberInfo(FindForm findForm) {
        return memberInfoRepository.validInfo(findForm.getPhoneNumber(), findForm.getUserRealName());
    }
}
