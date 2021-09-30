package com.cloth.wardrobe.web.auth;

import com.cloth.wardrobe.filter.ApiAuthorizationFilter;
import com.cloth.wardrobe.filter.ExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
//Spring Security 설정 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApiAuthorizationFilter apiAuthorizationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                //.and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 방지

                .and()
                    .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/image/**", "/js/**", "/profile","/api/v1/**").permitAll() // 전체 열람 권한
                    //.antMatchers("/api/v1/**").hasRole(Role.USER.name()) // USER 권한 사용자만 접근 가능하게 구현
                    .anyRequest().authenticated()  // 나머지 URL은 authenticate 즉, 로그인된 사용자만 접근 가능
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃 성공시 "/"로 이동
                .and()
                    .oauth2Login() // oauth2 로그인에 대한 설정 시작
        //            .successHandler(successHandler)
                    .userInfoEndpoint().userService(customOAuth2MemberService); // 로그인 성공시 후속조치를 할 Service 등록

        // 모든 요청에 토큰을 검증하는 필터를 추가한다.
        http.addFilterBefore(apiAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, ApiAuthorizationFilter.class);
    }
}
