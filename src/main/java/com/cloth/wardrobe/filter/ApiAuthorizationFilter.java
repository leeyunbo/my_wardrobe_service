package com.cloth.wardrobe.filter;

import com.cloth.wardrobe.exception.ApiKeyInvalidException;
import com.cloth.wardrobe.properties.AuthorizationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ApiAuthorizationFilter implements Filter {

    @Autowired
    private AuthorizationProperties authorizationProperties;

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if (requestMatcher.matches(httpServletRequest)) {
            String header = httpServletRequest.getHeader(authorizationProperties.getAuth_header());

            if (header == null) {
                throw new ApiKeyInvalidException("api key not found");
            } else if (!header.equals(authorizationProperties.getAuth_value())) {
                throw new ApiKeyInvalidException("api key is invalid");
            }
        }

        chain.doFilter(request, response);

    }
}
