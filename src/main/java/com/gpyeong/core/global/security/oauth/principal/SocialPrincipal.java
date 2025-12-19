package com.gpyeong.core.global.security.oauth.principal;

import com.gpyeong.core.domain.auth.domain.entity.OAuthProvider;

public interface SocialPrincipal {
	
	String getProviderId();
	
	OAuthProvider getProvider();
	
	String getEmail();
	
	String getName();
	
	String getPicture();
}
