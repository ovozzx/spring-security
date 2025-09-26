package com.ktdsuniversity.edu.member.vo;

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

	@Override
	public String toString() {
		return "RequestRegistMemberVO [email=" + email + ", name=" + name + ", password=" + password
				+ ", passwordConfirm=" + passwordConfirm + "]";
	}

}
