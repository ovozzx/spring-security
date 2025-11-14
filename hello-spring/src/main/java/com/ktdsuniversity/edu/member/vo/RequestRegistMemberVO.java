package com.ktdsuniversity.edu.member.vo;

import com.ktdsuniversity.edu.common.security.oauth.provider.vo.OAuthProviderVO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RequestRegistMemberVO {

	@NotEmpty(message = "필수 입력입니다.")
	@Email(message="이메일 형태로 작성하세요.")
	private String email;
	
	@NotEmpty(message = "필수 입력입니다.")
	private String name;
	
	@NotEmpty(message = "필수 입력입니다.")
	private String password;
	
	@NotEmpty(message = "필수 입력입니다.")
	private String passwordConfirm;

	private String salt;
	
	private OAuthProviderVO oAuthProviderVO;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public OAuthProviderVO getoAuthProviderVO() {
		return oAuthProviderVO;
	}

	public void setoAuthProviderVO(OAuthProviderVO oAuthProviderVO) {
		this.oAuthProviderVO = oAuthProviderVO;
	}

	@Override
	public String toString() {
		return "RequestRegistMemberVO [email=" + email + ", name=" + name + ", password=" + password
				+ ", passwordConfirm=" + passwordConfirm + ", salt=" + salt + ", oAuthProviderVO=" + oAuthProviderVO
				+ "]";
	}



}
