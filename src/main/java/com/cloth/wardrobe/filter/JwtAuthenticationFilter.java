package com.cloth.wardrobe.filter;

import com.cloth.wardrobe.dto.common.ResponseMessage;
import com.cloth.wardrobe.entity.member.Role;
import com.cloth.wardrobe.exception.JwtAuthenticaitonException;
import com.cloth.wardrobe.properties.AuthorizationProperties;
import com.cloth.wardrobe.service.JwtService;
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
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private Environment env;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthorizationProperties authorizationProperties;

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // permitAll이라고 해서 필터가 아예 적용되지 않도록 하는 것이 아니다. 필터는 항상 통과한다.
        //
        if (requestMatcher.matches(request)) {

            String jwt = request.getHeader(authorizationProperties.getAuth_header()).replace("Bearer ", "");

            if (jwt.isEmpty()) {
                throw new JwtAuthenticaitonException(ResponseMessage.INVALID_AUTHENTICATION);
            } else {

                // JWT가 있다면 유효한지 검사
                if (!jwtService.isValidateToken(jwt)) {
                    throw new JwtAuthenticaitonException(ResponseMessage.INVALID_AUTHENTICATION);
                }

                // JWT가 만료되었는지 확인
                if (jwtService.isTokenExpired(jwt)) {
                    throw new JwtAuthenticaitonException(ResponseMessage.EXPIRED_AUTHENTICATION);
                }

                Map<String, Object> attributes = jwtService.getBobyFromToken(jwt);

                // JWT로부터 사용자 정보를 추출하여 인증 정보를 만든 후 SecurityContext에 넣는다.
                // 결과적으로 처음 인증 공급자로부터 받은 정보를 JWT에 넣었고 헤더 통해 다시 받으면
                // 그것을 OAuth2User로 다시 복원해서 시큐리티의 인증정보에 넣어야 시큐리티의 필터들을 통과할 수 있다.

                // JWT를 만들 때 사용자 고유 ID로 삼았던 필드명과 맞춘다.
                String userNameAttributeName = "sub";

                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(Role.USER.getKey()));

                OAuth2User userDetails = new DefaultOAuth2User(authorities, attributes, userNameAttributeName);
                OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails, authorities, userNameAttributeName);

                authentication.setDetails(userDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);
    }
}
