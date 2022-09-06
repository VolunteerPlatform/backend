package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.controller.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.controller.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.controller.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.controller.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member1365Info;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MemberService {

    Long memberSignUp(MemberForm memberForm, Member member, MemberInfo memberInfo, Member1365Info member1365Info);

//    Long centerSignUp(CenterForm centerForm);

    String memberLogin(LoginForm loginForm, HttpServletResponse response);

//    void updateMember(MemberProfileUpdateDto memberProfileUpdateDto);

//    void updateMemberPwd(MemberPwdUpdateDto memberPwdUpdateDto);

//    String memberCertification(CertificationDto certificationDto);
//
//    void memberWithdrawal(WithdrawalForm withdrawalForm);
//
//    String findUsername(MemberInfo memberInfo);
}
