package com.gpyeong.core.global.security.oauth.principal;

import java.util.Map;

import com.gpyeong.core.domain.auth.domain.entity.OAuthProvider;

public class GooglePrincipal extends AbstractOAuth2Principal {
	
	public GooglePrincipal(Map<String, Object> attributes) {
		super(attributes, OAuthProvider.GOOGLE);
	}
	
	@Override
	public String getProviderId() {
		return (String) attributes.get("sub");
	}
	
	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}
	
	@Override
	public String getName() {
		return (String) attributes.get("name");
	}
	
	@Override
	public String getPicture() {
		return (String) attributes.get("picture");
	}
}
