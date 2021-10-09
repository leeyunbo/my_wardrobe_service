package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.dto.RequestForMember;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.service.JwtService;
import com.cloth.wardrobe.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final MemberService memberService;

    @PostMapping("/token")
    public ResponseEntity<Response> findJwtToken(@RequestBody String idToken, HttpServletResponse httpServletResponse) {
        RequestForMember requestForMember = memberService.validateGoogleToken(idToken);
        return jwtService.generateJwtToken(requestForMember, httpServletResponse);
    }
}
