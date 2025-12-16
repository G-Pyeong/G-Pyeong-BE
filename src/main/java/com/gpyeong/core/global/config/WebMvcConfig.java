package com.gpyeong.core.global.config;

import com.gpyeong.core.global.interceptor.JwtBlacklistInterceptor;
import com.gpyeong.core.global.resolver.AccessTokenArgumentResolver;
import com.gpyeong.core.global.resolver.CurrentUserArgumentResolver;
import com.gpyeong.core.global.resolver.RefreshTokenArgumentResolver;
import com.gpyeong.core.global.security.ExcludeBlacklistPathProperties;
import com.gpyeong.core.global.security.TokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenProvider tokenProvider;
    private final JwtBlacklistInterceptor jwtBlacklistInterceptor;
    private final ExcludeBlacklistPathProperties excludeBlacklistPathProperties;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(List.of(
                new CurrentUserArgumentResolver(tokenProvider),
                new RefreshTokenArgumentResolver(tokenProvider),
                new AccessTokenArgumentResolver(tokenProvider)
        ));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtBlacklistInterceptor)
                .excludePathPatterns(excludeBlacklistPathProperties.getExcludeAuthPaths());
    }
}