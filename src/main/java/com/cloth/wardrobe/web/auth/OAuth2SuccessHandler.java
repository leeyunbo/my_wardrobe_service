package com.cloth.wardrobe.web.auth;

import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.member.Role;
import com.cloth.wardrobe.web.MemberRequestMapper;
import com.cloth.wardrobe.web.auth.dto.OAuthAttributes;
import com.cloth.wardrobe.web.auth.dto.RequestForMember;
import com.cloth.wardrobe.web.auth.dto.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;
    private final MemberRequestMapper memberRequestMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        RequestForMember requestForMember = memberRequestMapper.toRequestDto(oAuth2User);

        Token token = tokenService.generateToken(requestForMember.getEmail(), Role.USER.getKey());

        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
        response.addHeader("Auth", token.getToken());
        response.addHeader("Refresh", token.getRefreshToken());
    }
}
