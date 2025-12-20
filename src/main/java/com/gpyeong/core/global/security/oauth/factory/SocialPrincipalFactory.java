package com.gpyeong.core.global.security.oauth.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gpyeong.core.domain.auth.domain.entity.OAuthProvider;
import com.gpyeong.core.global.security.oauth.principal.GooglePrincipal;
import com.gpyeong.core.global.security.oauth.principal.SocialPrincipal;

@Component
public class SocialPrincipalFactory {
	
	public SocialPrincipal createPrincipal(OAuthProvider provider, Map<String, Object> attributes) {
		return switch (provider) {
			case GOOGLE -> new GooglePrincipal(attributes);
			default -> throw new IllegalArgumentException("Unsupported provider: " + provider);
		};
	}
	
	public SocialPrincipal createPrincipal(String registrationId, Map<String, Object> attributes) {
		OAuthProvider provider = fromRegistrationId(registrationId);
		return createPrincipal(provider, attributes);
	}
	
	private OAuthProvider fromRegistrationId(String registrationId) {
		for (OAuthProvider provider : OAuthProvider.values()) {
			if (provider.getRegistrationId().equalsIgnoreCase(registrationId)) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unknown registrationId: " + registrationId);
	}
}
