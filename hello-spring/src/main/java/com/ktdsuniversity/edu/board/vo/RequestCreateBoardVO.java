package com.ktdsuniversity.edu.board.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RequestCreateBoardVO {

	private String id;
	@NotEmpty(message = "필수 입력입니다.")
	private String subject;
	
	@NotEmpty(message = "필수 입력입니다.")
	@Email(message = "이메일 형태로 작성하세요.")
	private String email;
	
	@NotEmpty(message = "필수 입력입니다.")
	private String content;
	private String fileGroupId;
	
	private List<MultipartFile> file;
	
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
	
	public List<MultipartFile> getFile() {
		return file;
	}
	
	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
	
	public String getFileGroupId() {
		return this.fileGroupId;
	}
	
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	@Override
	public String toString() {
		return "RequestCreateBoardVO [subject=" + this.subject + ", email=" + this.email + ", content=" + this.content
				+ "]";
	}

}
