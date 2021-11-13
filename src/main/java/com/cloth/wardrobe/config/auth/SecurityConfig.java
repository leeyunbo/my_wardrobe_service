package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.entity.member.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http
                 .csrf().disable()
                 .headers().frameOptions().disable()
                 .and()
                 .authorizeRequests()
                 .antMatchers("/", "/css/**", "/images/**", "/js/**").permitAll()
                 .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                 .anyRequest().authenticated()
                 .and()
                 .logout()
                 .logoutSuccessUrl("/")
                 .and()
                 .oauth2Login()
                 .userInfoEndpoint()
                 .userService(customOAuth2UserService);
    }
}
