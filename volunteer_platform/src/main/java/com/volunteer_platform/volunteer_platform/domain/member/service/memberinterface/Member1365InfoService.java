package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;

import java.util.Optional;

public interface Member1365InfoService {

    void createMember1365Info(MemberForm memberForm, Optional<Member> member);
}
