//package com.volunteer_platform.volunteer_platform.domain.member.controller.dto;
//
//import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
//import com.volunteer_platform.volunteer_platform.domain.member.models.MemberRole;
//import lombok.Builder;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@Getter
//public class AuthMemberDto extends User {
//
//    private Long id;
//    private String loginId;
//    private String password;
//    private Collection<GrantedAuthority> authorities;
//
//    public AuthMemberDto(String username, String password, Collection<? extends GrantedAuthority> authorities,
//                         Long id, String loginId, String password1,
//                         Collection<GrantedAuthority> authorities1) {
//        super(username, password, authorities);
//        this.id = id;
//        this.loginId = loginId;
//        this.password = password1;
//        this.authorities = authorities1;
//    }
//
//    public static AuthMemberDto of(Member member) {
//        SimpleGrantedAuthority[] simpleGrantedAuthorities = new SimpleGrantedAuthority[1];
//        StringBuilder stringBuilder = (new StringBuilder()).append("ROLE_");
//        MemberRole memberRole = member.getMemberRole();
//        simpleGrantedAuthorities[0] = new SimpleGrantedAuthority(stringBuilder.append(memberRole.name()).toString());
//
//        return new AuthMemberDto(
//                member.getLoginId(),
//                member.getPassword(),
//                member.getId(),
//                simpleGrantedAuthorities[0]
//        )
//    }
//}
