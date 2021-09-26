package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.entity.member.Role;
import com.cloth.wardrobe.web.auth.TokenService;
import com.cloth.wardrobe.web.auth.dto.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class TokenController {

    private final TokenService tokenService;

    @GetMapping("/token/expired")
    public void auth() {
        throw new RuntimeException();
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<Response> refreshAuth(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @CookieValue(name = "Refresh", required = false) String token) {
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            Token newToken = tokenService.generateJwtToken(email, Role.USER.getKey());

            Cookie authCookie = new Cookie("Auth", newToken.getToken());
            Cookie refreshCookie = new Cookie("Refresh", newToken.getRefreshToken());

            httpServletResponse.addCookie(authCookie);
            httpServletResponse.addCookie(refreshCookie);

            Response response = new Response();
            response.set_code(200);
            response.set_message("OK");

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        throw new RuntimeException();
    }
}
