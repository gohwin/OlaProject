package com.ola.security;

import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
            .authorizeRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
            .and()
            .csrf().disable()
            .formLogin()
                .loginPage("/system/login")
                .successHandler((request, response, authentication) -> {
                    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                    if (roles.contains("ROLE_ADMIN")) {
                        response.sendRedirect("/adminMain");
                    } else {
                        response.sendRedirect("/main");
                    }
                })
                .permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/system/accessDenied")
            .and()
            .logout()
                .logoutUrl("/system/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/main")
            .and()
            .sessionManagement()
                .invalidSessionUrl("/system/login") // 세션 만료 시 리디렉션할 URL
                .maximumSessions(1).sessionRegistry(sessionRegistry()) // 동시 세션 수 제한
                .expiredUrl("/system/login") // 세션 만료 시 리디렉션할 URL
            .and()
            .and()
            .userDetailsService(securityUserDetailsService);

        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    // 세션 레지스트리 빈을 추가하여 동시 세션 관리를 활성화합니다.
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
