package com.iucyh.jjapcloud.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = UUID.randomUUID().toString();
        MDC.put("traceId", uuid);

        long now = System.currentTimeMillis();
        request.setAttribute("startTime", now);

        log.info("REQUEST: {}", request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        log.info("RESPONSE: {} - {} ms", response.getStatus(), (endTime - startTime));
    }
}
