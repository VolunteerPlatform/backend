//package com.volunteer_platform.volunteer_platform.domain.member.controller.dto;
//
//import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
//import lombok.Builder;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@Data
//@Builder
//public class AuthMemberDto extends User {
//
//    private Long id;
//    private String loginId;
//    private String password;
//    private Collection<GrantedAuthority> authorities;
//
//    public AuthMemberDto(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//    }
//
//    public AuthMemberDto(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    }
//
//    public static AuthMemberDto of(Member member) {
//        return AuthMemberDto.builder()
//                .id(member.getId())
//                .loginId(member.getLoginId())
//                .password(member.getPassword())
//                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + member.getMemberRole().name())))
//                .build();
//
//    }
//}
