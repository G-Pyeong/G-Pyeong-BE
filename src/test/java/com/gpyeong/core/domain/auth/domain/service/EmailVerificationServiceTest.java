package com.gpyeong.core.domain.auth.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.gpyeong.core.global.exception.RestApiException;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailVerificationService, "allowedEmailDomain", "gachon.ac.kr");
        org.mockito.Mockito.lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("인증 코드 전송 실패 - 허용되지 않은 도메인")
    void sendCodeFail_InvalidDomain() {
        // given
        String email = "test@naver.com";

        // when & then
        assertThatThrownBy(() -> emailVerificationService.sendCode(email))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    @DisplayName("인증 코드 전송 성공 - 허용된 도메인")
    void sendCodeSuccess() {
        // given
        String email = "test@gachon.ac.kr";

        // when
        emailVerificationService.sendCode(email);

        // then
        verify(valueOperations).set(eq("email:code:" + email), any(String.class), any(Duration.class));
        verify(emailService).sendVerificationCode(eq(email), any(String.class));
    }

    @Test
    @DisplayName("이메일 인증 성공 - 코드 일치")
    void verifyCodeSuccess() {
        // given
        String email = "test@gachon.ac.kr";
        String code = "123456";

        given(valueOperations.get("email:code:" + email)).willReturn(code);

        // when
        emailVerificationService.verifyCode(email, code);

        // then
        verify(valueOperations).set(eq("email:verified:" + email), eq("true"), any(Duration.class));
        verify(redisTemplate).delete("email:code:" + email);
    }

    @Test
    @DisplayName("이메일 인증 실패 - 코드 불일치")
    void verifyCodeFail_Mismatch() {
        // given
        String email = "test@gachon.ac.kr";
        String code = "123456";
        String wrongCode = "000000";

        given(valueOperations.get("email:code:" + email)).willReturn(wrongCode);

        // when & then
        assertThatThrownBy(() -> emailVerificationService.verifyCode(email, code))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
