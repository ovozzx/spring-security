package com.ktdsuniversity.edu.board.vo;

import jakarta.validation.constraints.NotEmpty;

public class RequestModifyBoardVO {

	private String id;
	@NotEmpty(message="필수 입력입니다.")
	private String subject;
	
	private String email;
	
	@NotEmpty(message="필수 입력입니다.")
	private String content;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "RequestModifyBoardVO [id=" + this.id + ", subject=" + this.subject + ", email=" + this.email
				+ ", content=" + this.content + "]";
	}

}
