package com.volunteer_platform.volunteer_platform.domain.member.service;

import com.volunteer_platform.volunteer_platform.domain.member.models.Member;
import com.volunteer_platform.volunteer_platform.domain.member.models.MemberRole;
import com.volunteer_platform.volunteer_platform.domain.member.models.MembershipStatus;
import com.volunteer_platform.volunteer_platform.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    public void initMember() {
        Member member = Member.builder()
                .loginId("lee")
                .password(passwordEncoder.encode("test"))
                .kakaoId("")
                .membershipStatus(MembershipStatus.REGISTERED)
                .memberRole(MemberRole.MEMBER)
                .build();

        memberRepository.save(member);
    }

    @Test
    @DisplayName("로그인 test")
    public void loginSuccessTest() throws Exception {
        // given
        String loginId = "lee";
        String password = "test";

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

        mockMvc.perform(formLogin("/members/login").user(loginId).password(password))
                .andExpect(authenticated());
    }
}
