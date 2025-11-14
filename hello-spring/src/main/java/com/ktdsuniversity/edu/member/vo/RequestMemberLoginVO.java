package com.ktdsuniversity.edu.member.vo;

import com.ktdsuniversity.edu.common.security.oauth.provider.vo.OAuthProviderVO;

import jakarta.validation.constraints.NotEmpty;

public class RequestMemberLoginVO {

	@NotEmpty(message = "필수 입력입니다.")
	private String email;

	@NotEmpty(message = "필수 입력입니다.")
	private String password;

	private OAuthProviderVO oAuthProviderVO;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public OAuthProviderVO getoAuthProviderVO() {
		return oAuthProviderVO;
	}

	public void setoAuthProviderVO(OAuthProviderVO oAuthProviderVO) {
		this.oAuthProviderVO = oAuthProviderVO;
	}

	@Override
	public String toString() {
		return "RequestMemberLoginVO [email=" + email + ", password=" + password + ", oAuthProviderVO="
				+ oAuthProviderVO + "]";
	}



}
