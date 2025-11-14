package com.ktdsuniversity.edu.common.security.oauth.provider.user.naver;

import java.util.Map;

import org.slf4j.Logger;

import com.ktdsuniversity.edu.common.security.oauth.provider.user.SecurityOAuth2UserInfo;


public class NaverOAuth2UserInfo implements SecurityOAuth2UserInfo{

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(NaverOAuth2UserInfo.class);
	
	private Map<String, Object> attributes;
	
	public NaverOAuth2UserInfo(Map<String, Object> attributes) {
		logger.debug("Naber OAuth Result : " + attributes);
		this.attributes = (Map<String, Object>) attributes.get("response"); // 응답 결과 중, response가 붙어있는 것만 가져옴 (연동 가이드 참고)
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getEmail() {
		return this.attributes.get("email").toString();
	}

	@Override
	public String getName() {
		return this.attributes.get("name").toString();
	}

	@Override
	public String get(String key) {
		return this.attributes.get(key).toString();
	}
	
	

}
