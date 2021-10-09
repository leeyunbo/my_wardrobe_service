package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.dto.GoogleOAuthRequest;
import com.cloth.wardrobe.config.auth.dto.GoogleOAuthResponse;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.common.ResponseMessage;
import com.cloth.wardrobe.properties.AuthorizationProperties;
import com.cloth.wardrobe.properties.GoogleOAuthProperties;
import com.cloth.wardrobe.service.MemberService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleOAuthProperties googleOAuthProperties;
    private final AuthorizationProperties authorizationProperties;
    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    @GetMapping("/login")
    public ResponseEntity<Response> login(@RequestParam(name="code") String code, HttpServletResponse httpServletResponse) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        //Google OAuth Access Token 요청을 위한 파라미터 세팅
        GoogleOAuthRequest googleOAuthRequestParam = GoogleOAuthRequest
                .builder()
                .clientId(googleOAuthProperties.getClient_id())
                .clientSecret(googleOAuthProperties.getClient_secret())
                .code(code)
                .redirectUri("http://localhost:8080/auth/login")
                .grantType("authorization_code").build();

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResponseEntity<String> resultEntity = restTemplate.postForEntity(googleOAuthProperties.getOauth_base_url(), googleOAuthRequestParam, String.class);

        GoogleOAuthResponse result = objectMapper.readValue(resultEntity.getBody(), new TypeReference<GoogleOAuthResponse>() {});

        String idToken = result.getIdToken();

        Response response = new Response();
        response.set_code(200);
        response.set_message(ResponseMessage.OK);
        httpServletResponse.setHeader(authorizationProperties.getAuth_header(), "Bearer " + idToken);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
