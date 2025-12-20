package com.gpyeong.core.domain.auth.ui;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpyeong.core.domain.auth.application.dto.request.EmailRequest;
import com.gpyeong.core.domain.auth.application.dto.request.EmailVerificationRequest;
import com.gpyeong.core.domain.auth.application.usecase.UserAuthUseCase;
import com.gpyeong.core.domain.auth.domain.service.EmailVerificationService;
import com.gpyeong.core.domain.auth.domain.service.TokenBlacklistService;
import com.gpyeong.core.global.interceptor.JwtBlacklistInterceptor;
import com.gpyeong.core.global.security.ExcludeBlacklistPathProperties;
import com.gpyeong.core.global.security.TokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(EmailController.class)
class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmailVerificationService emailVerificationService;
    
    // AuthController가 아니라서 UserAuthUseCase는 필요없을 수 있으나 
    // SecurityConfig 등에서 빈을 필요로 할 수 있어 안전하게 Mock
    @MockBean
    private UserAuthUseCase userAuthUseCase;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private TokenBlacklistService tokenBlacklistService;
    
    @MockBean
    private ExcludeBlacklistPathProperties excludeBlacklistPathProperties;

    @MockBean
    private JwtBlacklistInterceptor jwtBlacklistInterceptor; 

    @Test
    @DisplayName("인증 코드 전송 API")
    @WithMockUser
    void sendVerificationCode() throws Exception {
        // given
        org.mockito.BDDMockito.given(jwtBlacklistInterceptor.preHandle(
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any()
        )).willReturn(true);

        org.mockito.BDDMockito.given(excludeBlacklistPathProperties.getExcludeAuthPaths())
                .willReturn(java.util.List.of("/api/auth/email/**"));

        EmailRequest request = new EmailRequest("test@gachon.ac.kr");
        doNothing().when(emailVerificationService).sendCode(request.email());

        // when & then
        mockMvc.perform(post("/api/auth/email/send")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("인증 코드 확인 API")
    @WithMockUser
    void verifyCode() throws Exception {
        // given
        org.mockito.BDDMockito.given(jwtBlacklistInterceptor.preHandle(
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any(),
                org.mockito.ArgumentMatchers.any()
        )).willReturn(true);

        org.mockito.BDDMockito.given(excludeBlacklistPathProperties.getExcludeAuthPaths())
                .willReturn(java.util.List.of("/api/auth/email/**"));

        EmailVerificationRequest request = new EmailVerificationRequest("test@gachon.ac.kr", "123456");
        doNothing().when(emailVerificationService).verifyCode(request.email(), request.code());

        // when & then
        mockMvc.perform(post("/api/auth/email/verify")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
