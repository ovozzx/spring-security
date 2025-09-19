package com.ktdsuniversity.edu.board.vo;

public class RequestCreateBoardVO {

	private String id;
	private String subject;
	private String email;
	private String content;

	public String getId() {
		return id;
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
		return "RequestCreateBoardVO [subject=" + this.subject + ", email=" + this.email + ", content=" + this.content
				+ "]";
	}

}
