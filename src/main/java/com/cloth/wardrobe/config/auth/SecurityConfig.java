package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.filter.CustomServletWrappingFilter;
import com.cloth.wardrobe.filter.ExceptionHandlerFilter;
import com.cloth.wardrobe.filter.JwtAuthenticationFilter;
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

@Slf4j
@RequiredArgsConstructor
//Spring Security 설정 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomServletWrappingFilter customServletWrappingFilter;
    private final CustomOAuth2MemberService customOAuth2MemberService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/", "/css/*", "/js/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .oauth2Login()
                .userInfoEndpoint().userService(customOAuth2MemberService)
                .and()
                .successHandler(customOAuth2SuccessHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .deleteCookies("JSESSIONID");
//                .logoutSuccessHandler(customLogoutSuccessHandler)

        http
                .addFilterBefore(jwtAuthenticationFilter, OAuth2AuthorizationRequestRedirectFilter.class)
                .addFilterBefore(customServletWrappingFilter, JwtAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, CustomServletWrappingFilter.class);
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
