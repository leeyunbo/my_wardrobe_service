package com.cloth.wardrobe.filter;

import com.cloth.wardrobe.config.auth.dto.RequestForMember;
import com.cloth.wardrobe.dto.common.ResponseMessage;
import com.cloth.wardrobe.entity.member.Role;
import com.cloth.wardrobe.exception.GoogleIdAuthenticationException;
import com.cloth.wardrobe.exception.JwtAuthenticaitonException;
import com.cloth.wardrobe.properties.AuthorizationProperties;
import com.cloth.wardrobe.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthorizationProperties authorizationProperties;

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {

            String jwt = request.getHeader(authorizationProperties.getAuth_header()).replace("Bearer ", "");

            if (jwt.isEmpty()) {
                throw new JwtAuthenticaitonException(ResponseMessage.DO_NOT_FOUND_JWT_TOKEN);
            } else {
                try {
                    RequestForMember requestForMember = memberService.validateToken(request);

//                    Map<String, Object> attributes = new HashMap<>();
//
//                    String userNameAttributeName = "sub";
//
//                    List<GrantedAuthority> authorities = new ArrayList<>();
//                    authorities.add(new SimpleGrantedAuthority(Role.USER.getKey()));
//
//                    OAuth2User userDetails = new DefaultOAuth2User(authorities, attributes, userNameAttributeName);
//                    OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails, authorities, userNameAttributeName);
//
//                    authentication.setDetails(userDetails);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    request.setAttribute("member", requestForMember);
                } catch (GoogleIdAuthenticationException e) {
                    throw e;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
