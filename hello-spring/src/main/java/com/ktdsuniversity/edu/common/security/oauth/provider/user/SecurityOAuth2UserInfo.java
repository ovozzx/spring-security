package com.ktdsuniversity.edu.common.security.oauth.provider.user;

import java.util.Map;

/**
 * OAuth2 (Naver, Google, Kakao, Instagram, ... 을 통해 전달받은 사용자 정보를
 * 추출하는 인터페이스
 */
public interface SecurityOAuth2UserInfo {

	/**
	 * OAuth 제공자 (Naver, Google, ...)에서 전달해 주는 사용자의 정보 Set
	 * @return
	 */
	Map<String, Object> getAttributes();
	/**
	 * 사용자 정보 Set에서 email만 추출.
	 * @return
	 */
	String getEmail();
	/**
	 * 사용자 정보 Set에서 사용자 이름만 추출. 
	 * @return
	 */
	String getName();
	
	String get(String key);
	
}
