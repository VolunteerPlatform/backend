package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.controller.form.FindForm;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;

import java.util.Optional;

public interface MemberInfoService {

    void createMemberInfo(MemberForm memberForm, Optional<Member> member, MemberInfo memberInfo);
}
