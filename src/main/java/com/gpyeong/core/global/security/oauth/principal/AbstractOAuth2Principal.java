package com.gpyeong.core.global.security.oauth.principal;

import java.util.Map;

import com.gpyeong.core.domain.auth.domain.entity.OAuthProvider;

import lombok.Getter;

@Getter
public abstract class AbstractOAuth2Principal implements SocialPrincipal {
	
	protected Map<String, Object> attributes;
	protected OAuthProvider provider;
	
	public AbstractOAuth2Principal(Map<String, Object> attributes, OAuthProvider provider) {
		this.attributes = attributes;
		this.provider = provider;
	}
	
	@Override
	public abstract String getProviderId();
	
	@Override
	public abstract String getEmail();
	
	@Override
	public abstract String getName();
	
	@Override
	public OAuthProvider getProvider() {
		return provider;
	}
	
	@Override
	public String getPicture() {
		return null; // 기본값, 필요시 서브클래스에서 오버라이드
	}
}
