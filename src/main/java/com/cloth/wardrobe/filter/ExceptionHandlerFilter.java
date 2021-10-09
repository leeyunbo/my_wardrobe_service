package com.cloth.wardrobe.filter;

import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.exception.ApiKeyAuthenticationException;
import com.cloth.wardrobe.exception.GoogleIdAuthenticationException;
import com.cloth.wardrobe.exception.JwtAuthenticaitonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (ApiKeyAuthenticationException | JwtAuthenticaitonException | GoogleIdAuthenticationException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex){
        response.setStatus(status.value());
        response.setContentType("application/json");
        Response response1 = new Response();
        response1.set_message(ex.getMessage());
        response1.set_code(200);

        try{
            response.getWriter().write(objectMapper.writeValueAsString(response1));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
