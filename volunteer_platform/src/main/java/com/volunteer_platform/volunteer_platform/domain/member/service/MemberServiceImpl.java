package com.volunteer_platform.volunteer_platform.domain.member.service;

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
import com.volunteer_platform.volunteer_platform.domain.member.models.MembershipStatus;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.Member1365InfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberInfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberInfoService memberInfoService;
    private final Member1365InfoService member1365InfoService;
    private final MembershipService membershipService;

    /**
     * 사용자 회원 가입
     * @param memberForm
     * @return
     */
    @Override
    @Transactional
    public Long memberSignUp(MemberForm memberForm, Member member, MemberInfo memberInfo, Member1365Info member1365Info) {
        boolean pass = memberValidation(memberForm.getLoginId()); // 아이디 중복 검사

        if (!pass) {
            throw new IllegalStateException("아이디가 중복되었습니다.");
        }

        memberForm.encoding(passwordEncoder);

        Member memberData = memberForm.toEntity(member);

        memberRepository.save(memberData);

        Optional<Member> memberOptional = memberRepository.findMemberId(memberData.getId());
 
        memberInfoService.createMemberInfo(memberForm, memberOptional, memberInfo);
        member1365InfoService.createMember1365Info(memberForm, memberOptional, member1365Info);

        return member.getId();
    }


//    /**
//     * 센터 회원 가입
//     * @param centerForm
//     * @return
//     */
//    @Override
//    @Transactional
//    public Long centerSignUp(CenterForm centerForm) {
//        boolean pass = memberValidation(centerForm.getUserName()); // 아이디 중복 검사
//
//        if (pass) {
//            Long memberId = memberRepository.save(Member.builder()
//                    .userName(centerForm.getUserName())
//                    .password(passwordEncoder.encode(centerForm.getPassword()))
//                    .googleId("center")
//                    .kakaoId("center")
//                    .membershipStatus(MembershipStatus.ADMIN)
//                    .build()).getId();
//
//
//            return memberId;
//        } else throw new IllegalStateException("아이디가 중복되었습니다.");
//    }
//

    @Override
    @Transactional
    public String memberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.findByMemberId(loginForm.getLoginId() )
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        return "로그인 성공";
    }

    public boolean memberValidation(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);

        if (member.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
//
//    /**
//     * Member Profile 수정
//     * @param memberProfileUpdateDto
//     */
//    @Override
//    @Transactional
//    public void updateMember(MemberProfileUpdateDto memberProfileUpdateDto) {
//
//        memberId.getMemberInfo().updateMemberInfo(memberProfileUpdateDto);
//        memberId.getMember1365Info().updateMember1365Info(memberProfileUpdateDto);
//    }
//
//    @Override
//    @Transactional
//    public void updateMemberPwd(MemberPwdUpdateDto memberPwdUpdateDto) {
//
//        String originPwd = memberPwdUpdateDto.getOriginPwd();
//
//        if (passwordEncoder.matches(originPwd, memberId.getPassword())) {
//            String newPwd = passwordEncoder.encode(memberPwdUpdateDto.getNewPwd());
//            memberRepository.updateMemberPwd(newPwd, memberId.getUsername());
//        } else {
//            throw new IllegalStateException("try again");
//        }
//    }
//
//    /**
//     * 사용자 비밀번호 인증
//     * @param request
//     * @param certificationDto
//     * @return
//     */
//    @Override
//    public String memberCertification(CertificationDto certificationDto) {
//
//        if (!passwordEncoder.matches(certificationDto.getPassword(), memberId.getPassword())) {
//            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
//        }
//
//        return memberId.getUsername();
//    }
//
//    /**
//     * 사용자 회원 탙퇴
//     * @param request
//     * @param withdrawalForm
//     */
//    @Override
//    @Transactional
//    public void memberWithdrawal(WithdrawalForm withdrawalForm) {
//
//        // MembershipStatus REGISTERED -> WITHDRAWAL 으로 update
//        member.updateMembership();
//
//        // comment 저장
//        membershipService.createMembership(withdrawalForm, member);
//    }
//
//    /**
//     * member user name 반환
//     * @param memberInfo
//     * @return
//     */
//    @Override
//    public String findUsername(MemberInfo memberInfo) {
//        return memberRepository.findUserName(memberInfo);
//    }
}

