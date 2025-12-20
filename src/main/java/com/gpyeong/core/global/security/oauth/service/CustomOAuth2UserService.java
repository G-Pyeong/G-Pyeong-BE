package com.gpyeong.core.global.security.oauth.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.gpyeong.core.domain.auth.domain.entity.OAuthProvider;
import com.gpyeong.core.domain.auth.domain.entity.User;
import com.gpyeong.core.domain.auth.domain.repository.UserRepository;
import com.gpyeong.core.global.security.oauth.factory.SocialPrincipalFactory;
import com.gpyeong.core.global.security.oauth.principal.SocialPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	private final SocialPrincipalFactory principalFactory;
	private final UserRepository userRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		Map<String, Object> attributes = oAuth2User.getAttributes();
		
		// Factory를 사용하여 Principal 생성
		SocialPrincipal principal = principalFactory.createPrincipal(registrationId, attributes);
		
		log.info("OAuth2 Login - Provider: {}, ProviderId: {}, Email: {}", 
			principal.getProvider(), principal.getProviderId(), principal.getEmail());
		
		// User 로드 또는 생성
		User user = userRepository.findByProviderIdAndProvider(principal.getProviderId(), principal.getProvider())
			.orElseGet(() -> {
				log.info("Creating new user from OAuth2: {}", principal.getEmail());
				return createNewUser(principal);
			});
		
		// OAuth2User 반환 (Spring Security가 사용)
		return new CustomOAuth2User(principal, user);
	}
	
	private User createNewUser(SocialPrincipal principal) {
		User user = User.builder()
			.email(principal.getEmail())
			.name(principal.getName())
			.providerId(principal.getProviderId())
			.provider(principal.getProvider())
			// universityId, department, yearId, gradeId는 온보딩에서 설정
			.build();
		return userRepository.save(user);
	}
}
