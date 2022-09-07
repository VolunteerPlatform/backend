package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberRole;
import com.volunteer_platform.volunteer_platform.domain.member.models.MembershipStatus;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = findMemberByLoginId(loginId);

        if (member.getMembershipStatus() == MembershipStatus.WITHDRAWAL) {
            throw new UsernameNotFoundException("탈퇴한 회원");
        }

        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(MemberRole.MEMBER.name())
                .build();

//        return AuthMemberDto.of(member);
    }


    public Member findMemberByLoginId(String loginId) {
        return memberRepository.findByMemberId(loginId).orElseThrow(() -> new UsernameNotFoundException("아이디 및 비밀번호를 확인하시오"));
    }
}
