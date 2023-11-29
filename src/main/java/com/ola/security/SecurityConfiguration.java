package com.ola.security;

import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;

	// HttpSecurity securiry : 사용자 인증 정보를 가지고 있는 객체
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		security.authorizeRequests().requestMatchers("/").permitAll(); // url이 "/"인 경우 모두 접근 허가

		// authenticated() : id, pwd를 통해 사용자 인증이 된 사람만 접근할 수 있는 url
		security.authorizeRequests().requestMatchers("/user/**").authenticated();
		security.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN");

		security.csrf().disable(); // csrf : Cross Site Request Forgery 의 약자(SNS 사용자 ID를 도용한 웹사이트 공격)

		security.formLogin() // 사용자가 화면을 통한 로그인 사용 설정
		.loginPage("/system/login")
        .successHandler(new CustomAuthenticationSuccessHandler())
        .defaultSuccessUrl("/main", true);
				// 로그인에 사용할 URL 지정 -> 로그인 페이지 제공하는 메소드

		security.exceptionHandling()
				.accessDeniedPage("/system/accessDenied");

		security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/main");
		
		// Member 테이블에서 사용자 조회 후, UserDetails 객체 변환 서비스 지정
		security.userDetailsService(securityUserDetailsService);
		return security.build();
	}
	
	/*
	 *  비밀번호 암호화
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// 커스텀 AuthenticationSuccessHandler 구현
    private static class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            // 여기에서 추가적인 로직을 수행할 수 있습니다.
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // 로그인 성공 후의 동작을 정의하세요.

            response.sendRedirect("/main");
        }
    }
}
