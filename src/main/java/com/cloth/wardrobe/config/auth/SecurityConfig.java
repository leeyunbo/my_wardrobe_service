package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.filter.ApiAuthorizationFilter;
import com.cloth.wardrobe.filter.CustomServletWrappingFilter;
import com.cloth.wardrobe.filter.ExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
//Spring Security 설정 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApiAuthorizationFilter apiAuthorizationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomServletWrappingFilter customServletWrappingFilter;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/oauth2Login").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .oauth2Login()
                .loginPage("/oauth2Login")
                .redirectionEndpoint()
                .baseUri("/ouath2/callback/*")
                .and()
                .userInfoEndpoint().userService(customOAuth2MemberService)
                .and()
                .successHandler(customOAuth2SuccessHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .and()
                .addFilterBefore(jwtAutenticationFilter(), OAuth2AuthorizationRequestRedirectFilter.class);

        // 모든 요청에 토큰을 검증하는 필터를 추가한다.
        http.addFilterBefore(apiAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(customServletWrappingFilter, ApiAuthorizationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, CustomServletWrappingFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("1234"))
                .roles("USER");
    }

}
