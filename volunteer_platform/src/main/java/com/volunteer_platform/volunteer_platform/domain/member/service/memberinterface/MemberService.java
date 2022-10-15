package com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface;

import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.*;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;

import javax.servlet.http.HttpServletResponse;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.*;

public interface MemberService {

    Long memberSignUp(MemberForm memberForm);

    void centerSignUp(CenterForm centerForm);

    void memberLogin(LoginForm loginForm, HttpServletResponse response);

    void updateMember(Long memberId, MemberProfileUpdateDto memberProfileUpdateDto);

    void updateMemberPwd(Long memberId, MemberPwdUpdateDto memberPwdUpdateDto);

    void memberCertification(Long memberId, CertificationDto certificationDto);

    void memberWithdrawal(Long memberId, WithdrawalForm withdrawalForm);

    String findUsername(MemberInfo memberInfo);

    MemberDto getMemberProfile(Long memberId);

    void loginIdValidation(LoginForm loginForm);

    void editPassword(FindPasswordForm passwordForm);

    void refreshAccessToken();

    void centerLogout();
    
    DTOResponse editPassword(FindPasswordForm passwordForm);

    void updatePassword(EditPasswordForm passwordForm);
}
