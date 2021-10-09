package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.filter.CustomServletWrappingFilter;
import com.cloth.wardrobe.filter.ExceptionHandlerFilter;
import com.cloth.wardrobe.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
//Spring Security 설정 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomServletWrappingFilter customServletWrappingFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//
         http
                 .authorizeRequests()
//                .antMatchers("/api/v1/**").authenticated()
                 .antMatchers("/**").permitAll()
                 .and()
                 .csrf().disable()
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
       http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customServletWrappingFilter, JwtAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, CustomServletWrappingFilter.class);
    }
}
