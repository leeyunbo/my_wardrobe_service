package com.cloth.wardrobe.filter;

import com.cloth.wardrobe.exception.ApiKeyInvalidException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ApiAuthorizationFilter implements Filter {

    public static final String API_KEY = "fdasrv34atdzb4zeex7y";
    public static final String API_KEY_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        try {
            if (!httpServletRequest.getHeader(API_KEY_HEADER).equals(API_KEY)) {
                throw new ApiKeyInvalidException("api key is invalid");
            }
        } catch (NullPointerException e) {
            throw new ApiKeyInvalidException("api key not found");
        }

        chain.doFilter(request, response);
    }
}
