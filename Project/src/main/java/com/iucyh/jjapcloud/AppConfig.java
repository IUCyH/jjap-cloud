package com.iucyh.jjapcloud;

import com.iucyh.jjapcloud.common.argumentresolver.LoginUserArgumentResolver;
import com.iucyh.jjapcloud.common.interceptor.CsrfTokenCheckInterceptor;
import com.iucyh.jjapcloud.common.interceptor.LoggingInterceptor;
import com.iucyh.jjapcloud.common.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://jjapcloud.website")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/ico/**", "/css", "/error");
        registry.addInterceptor(new CsrfTokenCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/login",
                        "/ico/**",
                        "/css",
                        "/error"
                );
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(3)
                .addPathPatterns(
                        "/users/me",
                        "/auth/logout",
                        "/musics",
                        "/musics/{id}"
                );
    }
}
