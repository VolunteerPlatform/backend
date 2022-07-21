package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MemberService {

    Long memberSignUp(MemberForm memberForm);

    Long centerSignUp(CenterForm centerForm);

    String memberLogin(LoginForm loginForm, HttpServletResponse response);

    boolean memberValidation(String userName);

    Member findMemberId(HttpServletRequest request);

    void updateMember(HttpServletRequest request, MemberProfileUpdateDto memberProfileUpdateDto);

    void updateMemberPwd(HttpServletRequest request, MemberPwdUpdateDto memberPwdUpdateDto);

    String memberCertification(HttpServletRequest request, CertificationDto certificationDto);

    void memberWithdrawal(HttpServletRequest request, WithdrawalForm withdrawalForm);

    String findUsername(MemberInfo memberInfo);
}
