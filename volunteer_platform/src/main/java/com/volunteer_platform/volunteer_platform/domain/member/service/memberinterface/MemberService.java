package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;

import javax.servlet.http.HttpServletResponse;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.*;

public interface MemberService {

    DTOResponse memberSignUp(MemberForm memberForm);

    DTOResponse centerSignUp(CenterForm centerForm);

    DTOResponse memberLogin(LoginForm loginForm, HttpServletResponse response);

    void updateMember(Long memberId, MemberProfileUpdateDto memberProfileUpdateDto);

    void updateMemberPwd(Long memberId, MemberPwdUpdateDto memberPwdUpdateDto);

    String memberCertification(Long memberId, CertificationDto certificationDto);

    void memberWithdrawal(Long memberId, WithdrawalForm withdrawalForm);

    String findUsername(MemberInfo memberInfo);

    DTOResponse getMemberProfile(Long memberId);
}
