package com.gpyeong.core.global.resolver;

import static com.gpyeong.core.global.exception.code.status.AuthErrorStatus.INVALID_REFRESH_TOKEN;
import static com.gpyeong.core.global.exception.code.status.GlobalErrorStatus._UNAUTHORIZED;

import com.gpyeong.core.global.annotation.RefreshToken;
import com.gpyeong.core.global.exception.RestApiException;
import com.gpyeong.core.global.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class RefreshTokenArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean supported = parameter.getParameterAnnotation(RefreshToken.class) != null
                && String.class.isAssignableFrom(parameter.getParameterType());
        return supported;
    }

    @Override
    public String resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        if (request == null) {
            throw new RestApiException(_UNAUTHORIZED);
        }

        String token = tokenProvider.getToken(request)
                .orElseThrow(() -> {
                    return new RestApiException(_UNAUTHORIZED);
                });

        if (tokenProvider.isAccessToken(token)) {
            throw new RestApiException(INVALID_REFRESH_TOKEN);
        }

        return token;
    }
}