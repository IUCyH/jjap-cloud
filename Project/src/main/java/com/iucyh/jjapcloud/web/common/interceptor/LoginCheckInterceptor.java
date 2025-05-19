package com.iucyh.jjapcloud.web.common.interceptor;

import com.iucyh.jjapcloud.web.common.exception.ServiceException;
import com.iucyh.jjapcloud.web.common.exception.errorcode.ServiceErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if(session == null) {
            throw new ServiceException(ServiceErrorCode.UNAUTHORIZED);
        }

        return true;
    }
}
