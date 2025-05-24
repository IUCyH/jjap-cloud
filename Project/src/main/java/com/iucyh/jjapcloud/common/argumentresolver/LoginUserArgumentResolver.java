package com.iucyh.jjapcloud.common.argumentresolver;

import com.iucyh.jjapcloud.common.annotation.LoginUser;
import com.iucyh.jjapcloud.web.dto.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(LoginUser.class);
        boolean assignable = UserDto.class.isAssignableFrom(parameter.getParameterType());

        return hasAnnotation && assignable;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest)webRequest.getNativeRequest();
        HttpSession session = httpRequest.getSession();
        if(session == null) {
            throw new RuntimeException("Session is null");
        }

        return session.getAttribute("user");
    }
}
