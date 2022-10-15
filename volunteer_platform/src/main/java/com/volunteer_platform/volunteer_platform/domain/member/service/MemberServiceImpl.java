package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenProvider;
import com.volunteer_platform.volunteer_platform.domain.member.auth.RefreshToken;
import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.*;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.member.repository.TokenRepository;
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
import java.util.Optional;
import static com.volunteer_platform.volunteer_platform.domain.volunteer.converter.CustomResponse.DTOResponse;

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

    private final HttpServletResponse response;
    private final HttpServletRequest request;

    /**
     * 사용자 회원 가입
     *
     * @param memberForm
     * @return
     */
    @Override
    @Transactional
    public Long memberSignUp(MemberForm memberForm) {
        if (!memberValidation(memberForm.getUserName())) {
            throw new IllegalArgumentException("아이디가 중복되었습니다.");
        }
        memberForm.encoding(passwordEncoder);

        Member member = memberForm.toEntity();
        memberRepository.save(member);

        Optional<Member> optionalMember = memberRepository.findMemberId(member.getId());

        memberInfoService.createMemberInfo(memberForm, optionalMember.orElseThrow());
        member1365InfoService.createMember1365Info(memberForm, optionalMember.orElseThrow());

        return member.getId();
    }


    /**
     * 센터 회원 가입
     *
     * @param centerForm
     * @return
     */
    @Override
    @Transactional
    public void centerSignUp(CenterForm centerForm) {
        if (!memberValidation(centerForm.getUserName())) {
            throw new IllegalArgumentException("아이디가 중복되었습니다.");
        }

        centerForm.encoding(passwordEncoder);

        Member member = centerForm.toEntity();
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void memberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.getMembersByMemberId(loginForm.getUserName());

        if (member == null || !(passwordEncoder.matches(loginForm.getPassword(), member.getPassword()))) {
            throw new IllegalArgumentException("로그인에 실패했습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        tokenRepository.save(new RefreshToken(refreshToken));
    }

    @Override
    public MemberDto getMemberProfile(Long memberId) {
        MemberDto memberProfile = memberRepository.getMemberProfile(memberId);

        return memberProfile;
    }

    @Override
    public void loginIdValidation(LoginForm loginForm) {
        if (!memberValidation(loginForm.getUserName())) {
            throw new IllegalArgumentException("아이디가 중복되었습니다.");
        }
    }

    @Override
    public void editPassword(FindPasswordForm passwordForm) {
        Optional<String> memberId = memberRepository.getMemberId(passwordForm.getUserName());
        if (memberId.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }

    @Override
    @Transactional
    public void updatePassword(EditPasswordForm passwordForm) {
        Optional<Member> member = memberRepository.findByUserName(passwordForm.getUserName());

        if (member.isPresent()) {
            String newPwd = passwordEncoder.encode(passwordForm.getPassword());
            member.get().updatePassword(newPwd);
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
    }

    /**
     * Member Profile 수정
     *
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
            throw new IllegalArgumentException("업데이트 실패했습니다.");
        }
    }

    /**
     * 사용자 비밀번호 인증
     *
     * @param memberId
     * @param certificationDto
     * @return
     */
    @Override
    public void memberCertification(Long memberId, CertificationDto certificationDto) {
        Member member = findMemberByMemberId(memberId);

        if (!passwordEncoder.matches(certificationDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
    }


    /**
     * 사용자 회원 탙퇴
     *
     * @param memberId
     * @param withdrawalForm
     */
    @Override
    @Transactional
    public void memberWithdrawal(Long memberId, WithdrawalForm withdrawalForm) {
        Member member = findMemberByMemberId(memberId);
        member.updateMembership();

        membershipService.createMembership(withdrawalForm, member);
    }

    /**
     * member user name 반환
     *
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

        return member.isEmpty();
    }

    /**
     * RefreshToken 으로 AccessToken 재발급
     */
    @Override
    public void refreshAccessToken() {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String accessToken = jwtTokenProvider.refreshAccessToken(refreshToken);

        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
    }

    @Override
    public void centerLogout() {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        tokenRepository.deleteById(refreshToken);
    }
}
