package com.cloth.wardrobe.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtService {

    @Autowired
    Environment env;

    // 토큰 발급
    public <T> String generateToken(T userDetails) {
        Map<String,Object> claim = new HashMap<>();

        if (userDetails instanceof DefaultOAuth2User) {

            claim.put("iss", env.getProperty("jwt.toekn-issuer"));  // 발급자
            claim.put("sub",  ((DefaultOAuth2User) userDetails).getName()); // subject 인증 대상(고유 ID)

            claim.put("email", ((DefaultOAuth2User) userDetails).getAttributes().get("userEmail"));
            claim.put("nickname", ((DefaultOAuth2User) userDetails).getAttributes().get("userName"));

        }
        //TODO 다른 타입의 사용자 정보의 경우는 나중에 생각해보자.
        // else if () {}


        String secret = env.getProperty("jwt.secret");
        int exp = Integer.valueOf(env.getProperty("jwt.expire-time"));

        claim.put("iat", new Date(System.currentTimeMillis()));
        claim.put("exp", new Date(System.currentTimeMillis() + (1000 * exp)));

        return Jwts.builder()
                .setClaims(claim)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // 토큰 유효성 검사
    public boolean isValidateToken(String token) {
        log.info("isValidateToken : " + getBobyFromToken(token).toString());
        final String subject = (String) getBobyFromToken(token).get("sub");
        return !subject.isEmpty();
    }

    // 토큰 만료 검사
    public boolean isTokenExpired(String token) {
        long exp = (Long) getBobyFromToken(token).get("exp");
        final Date expiration = new Date(exp);
        return expiration.before(new Date());
    }

    public Map<String,Object> getBobyFromToken(String token){
        String secret = env.getProperty("jwt.secret");
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
