package com.gpyeong.core.domain.auth.domain.service;

import java.time.Duration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private static final String refreshTokenPrefix = "REFRESH_TOKEN:";
	private final RedisTemplate<String, String> redisTemplate;

	public void saveRefreshToken(Integer userId, String refreshToken, Duration timeout) {
		redisTemplate.opsForValue().set(refreshTokenPrefix + userId, refreshToken, timeout);
	}

	public void deleteRefreshToken(Integer userId) {
		redisTemplate.delete(refreshTokenPrefix + userId);
	}

	public String findByUserId(Integer userId) {
		return redisTemplate.opsForValue().get(refreshTokenPrefix + userId);
	}
	public boolean isExist(String token, Integer userId) {
		String savedToken = redisTemplate.opsForValue().get(refreshTokenPrefix + userId);
		boolean exists = savedToken != null && Objects.equals(savedToken, token);

		return exists;
	}
}