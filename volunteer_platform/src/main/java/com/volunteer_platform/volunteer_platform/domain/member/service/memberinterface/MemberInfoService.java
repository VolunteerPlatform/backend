package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;

import java.util.Optional;

public interface MemberInfoService {

    void createMemberInfo(MemberForm memberForm, Member member);

    Optional<MemberInfo> validMemberInfo(FindForm findForm);
}
