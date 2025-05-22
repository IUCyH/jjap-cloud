package com.iucyh.jjapcloud.common.interceptor;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

public class CsrfTokenCheckInterceptor implements HandlerInterceptor {

    private static final List<String> whiteList = new ArrayList<>();

    public CsrfTokenCheckInterceptor() {
        whiteList.add("POST");
        whiteList.add("DELETE");
        whiteList.add("PATCH");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if(!whiteList.contains(method)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if(session == null) {
            throw new RuntimeException("Session is null");
        }

        String storedCsrfToken = (String)session.getAttribute("csrfToken");
        String csrfToken = request.getHeader("X-CSRF-Token");

        if(storedCsrfToken == null || !storedCsrfToken.equals(csrfToken)) {
            throw new ServiceException(ServiceErrorCode.UNAUTHORIZED);
        }
        return true;
    }
}
