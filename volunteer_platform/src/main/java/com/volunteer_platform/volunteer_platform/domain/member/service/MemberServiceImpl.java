package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenProvider;
import com.volunteer_platform.volunteer_platform.domain.member.auth.RefreshToken;
import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.models.MembershipStatus;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.member.repository.TokenRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.Member1365InfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberInfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MembershipService;
import com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.*;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenRepository tokenRepository;
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
    public Long memberSignUp(MemberForm memberForm) {
        boolean pass = memberValidation(memberForm.getUserName()); // 아이디 중복 검사

        if (pass) {
            Long memberId = memberRepository.save(Member.builder()
                    .userName(memberForm.getUserName())
                    .password(passwordEncoder.encode(memberForm.getPassword()))
                    .roles(Collections.singletonList("ROLE_USER")) // 일반 유저
                    .kakaoId("")
                    .membershipStatus(MembershipStatus.REGISTERED)
                    .build()).getId();

            Optional<Member> findMemberIdForMember = memberRepository.findMemberId(memberId);

            memberInfoService.createMemberInfo(memberForm, findMemberIdForMember);
            member1365InfoService.createMember1365Info(memberForm, findMemberIdForMember);

            return memberId;
        } else throw new IllegalStateException("아이디가 중복되었습니다.");
    }


    /**
     * 센터 회원 가입
     * @param centerForm
     * @return
     */
    @Override
    @Transactional
    public Long centerSignUp(CenterForm centerForm) {
        boolean pass = memberValidation(centerForm.getUserName()); // 아이디 중복 검사

        if (pass) {
            Long memberId = memberRepository.save(Member.builder()
                    .userName(centerForm.getUserName())
                    .password(passwordEncoder.encode(centerForm.getPassword()))
                    .roles(Collections.singletonList("ROLE_ADMIN")) // 일반 유저
                    .kakaoId("center")
                    .membershipStatus(MembershipStatus.ADMIN)
                    .build()).getId();


            return memberId;
        } else throw new IllegalStateException("아이디가 중복되었습니다.");
    }

    @Override
    @Transactional
    public DTOResponse memberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.findByUserName(loginForm.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        tokenRepository.save(new RefreshToken(refreshToken));

        String message = "로그인에 성공했습니다.";

        return new DTOResponse(HttpStatus.OK.value(), message, member.getId());
    }

    /**
     * Member Profile 수정
     * @param memberId
     * @param memberProfileUpdateDto
     */
    @Override
    @Transactional
    public void updateMember(Long memberId, MemberProfileUpdateDto memberProfileUpdateDto) {
        Member member = findMemberByMemberId(memberId);

        member.getMemberInfo().updateMemberInfo(memberProfileUpdateDto);
        member.getMember1365Info().updateMember1365Info(memberProfileUpdateDto);
    }

    @Override
    @Transactional
    public void updateMemberPwd(Long memberId, MemberPwdUpdateDto memberPwdUpdateDto) {
        Member member = findMemberByMemberId(memberId);

        String originPwd = memberPwdUpdateDto.getOriginPwd();

        if (passwordEncoder.matches(originPwd, member.getPassword())) {
            String newPwd = passwordEncoder.encode(memberPwdUpdateDto.getNewPwd());
            memberRepository.updateMemberPwd(newPwd, member.getUsername());
        } else {
            throw new IllegalStateException("try again");
        }
    }

    /**
     * 사용자 비밀번호 인증
     * @param memberId
     * @param certificationDto
     * @return
     */
    @Override
    public String memberCertification(Long memberId, CertificationDto certificationDto) {
        Member member = findMemberByMemberId(memberId);

        if (!passwordEncoder.matches(certificationDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        return member.getUsername();
    }

    /**
     * 사용자 회원 탙퇴
     * @param memberId
     * @param withdrawalForm
     */
    @Override
    @Transactional
    public void memberWithdrawal(Long memberId, WithdrawalForm withdrawalForm) {
        Member member = findMemberByMemberId(memberId);

        // MembershipStatus REGISTERED -> WITHDRAWAL 으로 update
        member.updateMembership();

        // comment 저장
        membershipService.createMembership(withdrawalForm, member);
    }

    /**
     * member user name 반환
     * @param memberInfo
     * @return
     */
    @Override
    public String findUsername(MemberInfo memberInfo) {
        return memberRepository.findUserName(memberInfo);
    }


    private Member findMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
    }

    private boolean memberValidation(String userName) {
        Optional<Member> member = memberRepository.findByUserName(userName);

        if (member.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}

