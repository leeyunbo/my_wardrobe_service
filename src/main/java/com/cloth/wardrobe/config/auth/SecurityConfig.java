package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.domain.member.MemberAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
//Spring Security 설정 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2MemberService customOAuth2MemberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점
                    .antMatchers("/", "/static/css/**", "/images/**", "/static/js/**").permitAll() // 전체 열람 권한
                    .antMatchers("/api/v1/**").hasRole(MemberAuthority.USER.name()) // USER 권한 사용자만 접근 가능하게 구현
                    .anyRequest().authenticated()  // 나머지 URL은 authenticate 즉, 로그인된 사용자만 접근 가능
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃 성공시 "/"로 이동
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2MemberService); //로그인 성공시 후속조치를 할 Service 등록
    }
}
