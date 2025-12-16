package com.gpyeong.core.domain.auth.domain.service;

import com.gpyeong.core.global.exception.RestApiException;
import com.gpyeong.core.global.exception.code.status.GlobalErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.gpyeong.core.global.util.RandomCodeGenerator;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final StringRedisTemplate redisTemplate;
    private final EmailService emailService;

    @org.springframework.beans.factory.annotation.Value("${app.auth.allowed-email-domain}")
    private String allowedEmailDomain;

    private static final String CODE_PREFIX = "email:code:";
    private static final String VERIFIED_PREFIX = "email:verified:";
    private static final Duration CODE_TTL = Duration.ofMinutes(5);
    private static final Duration VERIFIED_TTL = Duration.ofMinutes(30);

    private static final String RETRY_COUNT_PREFIX = "email:retry:count:";
    private static final String BACKOFF_PREFIX = "email:retry:backoff:";

    public void sendCode(String email) {
        if (!email.endsWith("@" + allowedEmailDomain)) {
             throw new RestApiException(GlobalErrorStatus._UNAUTHORIZED);
        }

        // 재전송 대기 시간 확인
        if (Boolean.TRUE.equals(redisTemplate.hasKey(BACKOFF_PREFIX + email))) {
            Long expire = redisTemplate.getExpire(BACKOFF_PREFIX + email); // Seconds
            long minutes = expire != null ? expire / 60 : 0;
            throw new IllegalArgumentException("재전송 대기 시간입니다. " + (minutes + 1) + "분 후에 다시 시도해주세요.");
        }

        // 6자리 인증 코드 생성
        String code = RandomCodeGenerator.generateCode();
        
        // Redis에 인증 코드 저장
        redisTemplate.opsForValue().set(CODE_PREFIX + email, code, CODE_TTL);
        
        // 재전송 횟수 증가 및 대기 시간 설정
        Long count = redisTemplate.opsForValue().increment(RETRY_COUNT_PREFIX + email);
        if (count != null && count == 1) {
            redisTemplate.expire(RETRY_COUNT_PREFIX + email, Duration.ofHours(24));
        }

        long waitMinutes = getNextBackoffMinutes(count != null ? count : 1);
        if (waitMinutes > 0) {
            redisTemplate.opsForValue().set(BACKOFF_PREFIX + email, "blocked", Duration.ofMinutes(waitMinutes));
        }
        
        // 이메일 전송
        emailService.sendVerificationCode(email, code);
    }

    private long getNextBackoffMinutes(long count) {
        if (count <= 1) return 0; // 1회차: 다음 재전송 즉시 가능
        if (count == 2) return 10; // 2회차 (재전송 1번째): 10분 대기
        if (count == 3) return 30; // 3회차: 30분 대기
        if (count == 4) return 60; // 4회차: 1시간 대기
        return 180; // 5회차 이상: 3시간 대기
    }

    public void verifyCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get(CODE_PREFIX + email);
        
        if (savedCode == null || !savedCode.equals(code)) {
            throw new IllegalArgumentException("인증 코드가 올바르지 않거나 만료되었습니다.");
        }
        
        // 인증 완료 상태로 표시
        redisTemplate.opsForValue().set(VERIFIED_PREFIX + email, "true", VERIFIED_TTL);
        
        // 사용된 인증 코드 삭제
        redisTemplate.delete(CODE_PREFIX + email);
    }

    public boolean isVerified(String email) {
        return Boolean.TRUE.toString().equals(redisTemplate.opsForValue().get(VERIFIED_PREFIX + email));
    }
}
