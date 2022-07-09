package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberInfoService {

    private final MemberInfoRepository memberInfoRepository;

    @Transactional
    public void saveMemberInfo(MemberInfo memberInfo) {
        memberInfoRepository.save(memberInfo);
    }

    @Transactional
    public void createMemberInfo(MemberForm memberForm, Optional<Member> member) {
        MemberInfo memberInfo = MemberInfo.builder()
                .birthday(memberForm.getBirthday())
                .gender(memberForm.getGender())
                .phoneNumber(memberForm.getPhoneNumber())
                .userRealName(memberForm.getUserRealName())
                .member(member.get())
                .build();

        memberInfoRepository.save(memberInfo);
    }

    /**
     * 회원가입한 유저인지 확인
     * @param findForm
     * @return
     */
    public Optional<MemberInfo> validMemberInfo(FindForm findForm) {
        return memberInfoRepository.validInfo(findForm.getPhoneNumber(), findForm.getUserRealName());
    }
}
