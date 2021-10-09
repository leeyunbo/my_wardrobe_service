package com.cloth.wardrobe.interceptor;

import com.cloth.wardrobe.entity.common.RequestLog;
import com.cloth.wardrobe.repository.RequestLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Autowired
    private RequestLogRepository requestLogRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String reqId = UUID.randomUUID().toString();
        request.setAttribute("ReqId", reqId);

        RequestLog requestLog = new RequestLog();

        requestLog.setUuid(reqId);
        requestLog.setClientIp(request.getRemoteAddr());

        String requestURL = request.getRequestURL().toString();
        Enumeration<String> paramKeys = request.getParameterNames();
        if(paramKeys.hasMoreElements()) requestURL += "?";
        while(paramKeys.hasMoreElements()) {
            String key = paramKeys.nextElement();
            requestURL += key + "=" + request.getParameter(key);
            if(paramKeys.hasMoreElements()) requestURL += "&";
        }
        requestLog.setRequestUri(requestURL);

        Date now = new Date();
        requestLog.setReqTime(Timestamp.valueOf(dateFormat.format(now)));

        Enumeration headerNames = request.getHeaderNames();
        String header = "";
        while(headerNames.hasMoreElements()) {
            String name = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            header += name + ": " + value + "\n";
        }
        requestLog.setHeader(header);

        requestLogRepository.save(requestLog);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String header = request.getHeader("Content-Type");
        if(header != null && header.equals("application/json; charset=UTF-8")) {
            final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
            final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

            Optional<RequestLog> log = requestLogRepository.findById(request.getAttribute("ReqId").toString());
            log.ifPresent(selectLog -> {
                try {
                    Date now = new Date();
                    selectLog.setResTime(Timestamp.valueOf(dateFormat.format(now)));
                    selectLog.setBody(objectMapper.readTree(cachingRequest.getContentAsByteArray()).toString());
                    selectLog.setResponseMsg(objectMapper.readTree(cachingResponse.getContentAsByteArray()).toString());
                    requestLogRepository.save(selectLog);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
