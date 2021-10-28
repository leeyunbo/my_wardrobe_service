package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // CustomAuthenticationFilter에서 생성된 토큰 조회
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        final String email = token.getName();
        final String name = (String) token.getCredentials();
        final CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        /**
         * 검증 코드 추가되면 추가할 것
         */

        return new UsernamePasswordAuthenticationToken(customUserDetails, name, customUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
