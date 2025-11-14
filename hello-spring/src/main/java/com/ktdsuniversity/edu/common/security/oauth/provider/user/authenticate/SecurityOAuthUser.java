package com.ktdsuniversity.edu.common.security.oauth.provider.user.authenticate;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

import com.ktdsuniversity.edu.common.security.oauth.provider.user.SecurityOAuth2UserInfo;
import com.ktdsuniversity.edu.common.security.user.SecurityUser;
import com.ktdsuniversity.edu.member.vo.MemberVO;

public class SecurityOAuthUser extends SecurityUser implements OAuth2User{

	private static final long serialVersionUID = 9159254750773917468L;

	private SecurityOAuth2UserInfo securityOAuth2UserInfo;
	
	public SecurityOAuthUser(MemberVO memberVO, SecurityOAuth2UserInfo securityOAuth2UserInfo) { // SecurityOAuth2UserInfo : 연동처에서 가지고 온 정보가 다 들어있음
		super(memberVO);
		this.securityOAuth2UserInfo = securityOAuth2UserInfo;
	}
	
	@Override
	public Map<String, Object> getAttributes() {
		return this.securityOAuth2UserInfo.getAttributes();
	}

	@Override
	public String getName() {
		return this.securityOAuth2UserInfo.getName();
	}

	public String getEmail() {
		return this.securityOAuth2UserInfo.getEmail();
	}
	
}
