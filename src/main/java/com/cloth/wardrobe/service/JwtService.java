package com.cloth.wardrobe.service;

import com.cloth.wardrobe.config.auth.dto.RequestForMember;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.common.ResponseMessage;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.member.MemberRepository;
import com.cloth.wardrobe.properties.AuthorizationProperties;
import com.cloth.wardrobe.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtService {

    private final MemberRepository memberRepository;
    private final AuthorizationProperties authorizationProperties;
    private final JwtProperties jwtProperties;

    @Transactional
    public ResponseEntity<Response> generateJwtToken(RequestForMember requestForMember, HttpServletResponse httpServletResponse) {
        Member member = memberRepository.findByEmail(requestForMember.getEmail())
                .map(entity -> entity.change(requestForMember.getName(), requestForMember.getPicture()))
                .orElse(requestForMember.toEntity(requestForMember));

        memberRepository.save(member);
        httpServletResponse.setHeader(authorizationProperties.getAuth_header(), generateToken(requestForMember));

        Response response = new Response();
        response.set_code(200);
        response.set_message(ResponseMessage.OK);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 토큰 발급
    public <T> String generateToken(RequestForMember requestForMember) {
        Map<String, Object> claim = new HashMap<>();

        claim.put("iss", jwtProperties.getTokenIssuer());  // 발급자
        claim.put("email", requestForMember.getEmail());
        claim.put("name", requestForMember.getName());
        claim.put("picture", requestForMember.getPicture());


        String secretKey = jwtProperties.getSecretKey();
        String expireTime = jwtProperties.getExpireTime();

        claim.put("iat", new Date(System.currentTimeMillis()));
        claim.put("exp", new Date(System.currentTimeMillis() + (1000 * Integer.parseInt(expireTime))));

        return Jwts.builder()
                .setClaims(claim)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // 토큰 유효성 검사
    public boolean isValidateToken(String token) {
        final String name = (String) getBodyFromToken(token).get("name");
        final String email = (String) getBodyFromToken(token).get("email");
        final String picture = (String) getBodyFromToken(token).get("picture");

        return !name.isEmpty() && !email.isEmpty();
    }

    // 토큰 만료 검사
    public boolean isTokenExpired(String token) {
        long exp = (Long) getBodyFromToken(token).get("exp");
        final Date expiration = new Date(exp);
        return expiration.before(new Date());
    }

    public Map<String,Object> getBodyFromToken(String token){
        String secretKey = jwtProperties.getSecretKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public RequestForMember getMemberInfoFromToken(Map<String, Object> attributes) {
        final String email = (String) attributes.get("email");
        final String name = (String) attributes.get("name");
        final String picture = (String) attributes.get("picture");

        return RequestForMember.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .build();
    }
}
