package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.controller.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;

public interface MembershipService {

    void createMembership(WithdrawalForm WithdrawalForm, Member member);
}
