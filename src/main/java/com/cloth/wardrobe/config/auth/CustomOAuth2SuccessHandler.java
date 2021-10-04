package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.properties.AuthorizationProperties;
import com.cloth.wardrobe.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OAuth2의 Resource Server로부터 인증이 성공한 후 취득한 사용자 정보를 처리하는 핸들러
 * 이 핸들러에서 다시 JWT Token을 발급한다
 *
 */
@Slf4j
@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthorizationProperties authorizationProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String jwt = jwtService.generateToken((DefaultOAuth2User) authentication.getPrincipal());
        log.info("onAuthenticationSuccess() " + jwt);

        response.setHeader(authorizationProperties.getAuth_header(), "Bearer " +  jwt);
    }
}
