package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.controller.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member1365Info;

import java.util.Optional;

public interface Member1365InfoService {

    void createMember1365Info(MemberForm memberForm, Optional<Member> member, Member1365Info member1365Info);
}
