package com.cloth.wardrobe.config.auth;

import org.hibernate.cfg.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OAuth2의 Resource Server로부터 인증이 성공한 후 취득한 사용자 정보를 처리하는 핸들러
 * 이 핸들러에서 다시 JWT Token을 발급한다
 *
 */
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private Environment env;
    private JwtUtils jwtUtils;
    private CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String jwt = jwtUtils.
    }
}
