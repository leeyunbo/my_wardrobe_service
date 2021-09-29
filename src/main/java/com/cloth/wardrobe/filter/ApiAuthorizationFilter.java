package com.cloth.wardrobe.filter;

import com.cloth.wardrobe.exception.ApiKeyInvalidException;
import com.cloth.wardrobe.properties.AuthorizationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ApiAuthorizationFilter implements Filter {

    @Autowired
    private AuthorizationProperties authorizationProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        try {
            if (!httpServletRequest.getHeader(authorizationProperties.getAuth_header()).equals(authorizationProperties.getAuth_value())) {
                throw new ApiKeyInvalidException("api key is invalid");
            }
        } catch (NullPointerException e) {
            throw new ApiKeyInvalidException("api key not found");
        }

        chain.doFilter(request, response);
    }
}
