package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.config.jwt.JwtTokenProvider;
import com.volunteer_platform.volunteer_platform.domain.member.auth.RefreshToken;
import com.volunteer_platform.volunteer_platform.domain.member.dto.CertificationDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberProfileUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.dto.MemberPwdUpdateDto;
import com.volunteer_platform.volunteer_platform.domain.member.form.CenterForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.LoginForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.MemberForm;
import com.volunteer_platform.volunteer_platform.domain.member.form.WithdrawalForm;
import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberInfo;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import com.volunteer_platform.volunteer_platform.domain.member.repository.TokenRepository;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.Member1365InfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberInfoService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MemberService;
import com.volunteer_platform.volunteer_platform.domain.member.service.memberinterface.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public DTOResponse centerSignUp(CenterForm centerForm) {
        if (!memberValidation(centerForm.getUserName())) {
            String errorMessage = "아이디가 중복되었습니다.";

            return new DTOResponse(HttpStatus.BAD_REQUEST.value(), errorMessage, errorMessage);
        }

        centerForm.encoding(passwordEncoder);

        Member member = centerForm.toEntity();
        memberRepository.save(member);

        String message = "정상적으로 회원가입이 되었습니다.";
        return new DTOResponse(HttpStatus.CREATED.value(), message, member.getId());
    }

    @Override
    @Transactional
    public DTOResponse memberLogin(LoginForm loginForm, HttpServletResponse response) {
        Member member = memberRepository.getMembersByMemberId(loginForm.getUserName());

        if (member == null || !(passwordEncoder.matches(loginForm.getPassword(), member.getPassword()))) {

            String message = "로그인에 실패했습니다.";

            return new DTOResponse(HttpStatus.BAD_REQUEST.value(), message, message);
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername(), member.getRoles());
        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);

        tokenRepository.save(new RefreshToken(refreshToken));

        String message = "로그인에 성공했습니다.";

        return new DTOResponse(HttpStatus.OK.value(), message, member.getId());
    }

    @Override
    public DTOResponse getMemberProfile(Long memberId) {
        MemberDto memberProfile = memberRepository.getMemberProfile(memberId);

        String message = "회원 정보를 성공적으로 불러왔습니다.";

        return new DTOResponse(HttpStatus.OK.value(), message, memberProfile);
    }

    @Override
    public DTOResponse loginIdValidation(LoginForm loginForm) {
        if (!memberValidation(loginForm.getUserName())) {
            String errorMessage = "아이디가 중복되었습니다.";

            return new DTOResponse(HttpStatus.BAD_REQUEST.value(), errorMessage, errorMessage);
        }

        String message = "회원가입 가능합니다.";

        return new DTOResponse(HttpStatus.OK.value(), message, message);
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
            throw new IllegalStateException("try again");
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
    public String memberCertification(Long memberId, CertificationDto certificationDto) {
        Member member = findMemberByMemberId(memberId);

        if (!passwordEncoder.matches(certificationDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        return member.getUsername();
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
