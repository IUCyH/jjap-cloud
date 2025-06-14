package com.iucyh.jjapcloud.common.interceptor;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.exception.errorcode.ServiceErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

public class LoginCheckInterceptor implements HandlerInterceptor {

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final Map<String, String> whiteList = new HashMap<>();

    public LoginCheckInterceptor() {
        whiteList.put("/musics", "GET");
        whiteList.put("/musics/{publicId}", "GET");
        whiteList.put("/musics/stream/{publicId}", "GET");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(shouldIgnore(request) || request.getMethod().equals("OPTIONS")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            throw new ServiceException(ServiceErrorCode.UNAUTHORIZED);
        }

        return true;
    }

    private boolean shouldIgnore(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        boolean ignore = false;

        for(String key : whiteList.keySet()) {
            if(pathMatcher.match(key, uri) && whiteList.get(key).equals(method)) {
                ignore = true;
                break;
            }
        }

        return ignore;
    }
}
