package com.volunteer_platform.volunteer_platform.config;

import com.volunteer_platform.volunteer_platform.domain.member.handler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/")
                .sessionRegistry(sessionRegistry());

        return httpSecurity.authorizeRequests()
                .antMatchers("/center/**").hasRole("ADMIN")
                .antMatchers("/members").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/members/deny").hasRole("MEMBER")
                .antMatchers("/members/login").anonymous()
                .antMatchers("/members/password").authenticated()
                .antMatchers("/members/logout").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/members/login")
                .loginProcessingUrl("/members/login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/")
                .and()
                .logout()
                .logoutUrl("/members/logout")
                .logoutSuccessUrl("/members/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .build();
    }
}
