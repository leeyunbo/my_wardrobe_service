package com.cloth.wardrobe.filter;

import com.cloth.wardrobe.exception.ApiKeyInvalidException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ApiAuthFilter implements Filter {

    private String principalRequestHeader = "Authorization";
    private String principalRequestValue = "fdasrv34atdzb4zeex7y";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        try {
            if (!httpServletRequest.getHeader(principalRequestHeader).equals(principalRequestValue)) {
                throw new ApiKeyInvalidException("api key is invalid");
            }
        } catch (NullPointerException e) {
            throw new ApiKeyInvalidException("api key not found");
        }

        chain.doFilter(request, response);
    }
}
