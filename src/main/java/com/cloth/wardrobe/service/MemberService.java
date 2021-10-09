package com.cloth.wardrobe.service;

import com.cloth.wardrobe.config.auth.dto.RequestForMember;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.common.ResponseMessage;
import com.cloth.wardrobe.entity.member.MemberRepository;
import com.cloth.wardrobe.exception.GoogleIdAuthenticationException;
import com.cloth.wardrobe.properties.AuthorizationProperties;
import com.cloth.wardrobe.properties.GoogleOAuthProperties;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final AuthorizationProperties authorizationProperties;
    private final GoogleOAuthProperties oAuthProperties;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<Response> updateMemberFromIdToken(RequestForMember requestForMember) {
        memberRepository.findByEmail(requestForMember.getEmail())
                .map(entity -> entity.change(requestForMember.getName(), requestForMember.getPicture()))
                .orElse(requestForMember.toEntity(requestForMember));

        Response response = new Response();
        response.set_code(200);
        response.set_message(ResponseMessage.OK);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public RequestForMember validateToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(authorizationProperties.getAuth_header()).replace("Bearer ", "");

        if(token.isEmpty())
            throw new GoogleIdAuthenticationException(ResponseMessage.DO_NOT_FOUND_GOOGLE_ID_TOKEN);

        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jsonFactory)
                .setAudience(Collections.singletonList(oAuthProperties.getClient_id()))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(token);
            GoogleIdToken.Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String picture = (String) payload.get("picture");

            return new RequestForMember(name, email, picture);
        } catch (GeneralSecurityException | IOException e) {
            throw new GoogleIdAuthenticationException(ResponseMessage.INVALID_GOOGLE_ID_TOKEN);
        }
    }
}
