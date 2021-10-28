package com.cloth.wardrobe.filter;

import com.cloth.wardrobe.config.auth.dto.RequestForMember;
import com.cloth.wardrobe.exception.GoogleIdAuthenticationException;
import com.cloth.wardrobe.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private MemberService memberService;

    public CustomAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

        try {
            RequestForMember requestForMember = memberService.validateToken(request);
            memberService.updateMemberFromIdToken(requestForMember);
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(requestForMember.getEmail(), requestForMember.getName());
        } catch (Exception e) {
            throw new GoogleIdAuthenticationException();
        }

        setDetails(request, usernamePasswordAuthenticationToken);
        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }
}
