package com.ktdsuniversity.edu.member.vo;

import jakarta.validation.constraints.NotEmpty;

public class RequestMemberLoginVO {

	@NotEmpty(message = "필수 입력입니다.")
	private String email;

	@NotEmpty(message = "필수 입력입니다.")
	private String password;

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

	@Override
	public String toString() {
		return "RequestMemberLoginVO [email=" + email + ", password=" + password + "]";
	}

}
