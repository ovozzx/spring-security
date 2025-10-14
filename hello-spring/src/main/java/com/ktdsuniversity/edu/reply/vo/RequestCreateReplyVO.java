package com.ktdsuniversity.edu.reply.vo;

import org.springframework.web.multipart.MultipartFile;

public class RequestCreateReplyVO {

	private String replyId;
	private String parentReplyId;

	private String boardId;
	private MultipartFile replyAttachFile;
	private String replyContent;
	private String fileGroupId;
	private String email;

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getParentReplyId() {
		return parentReplyId;
	}

	public void setParentReplyId(String parentReplyId) {
		this.parentReplyId = parentReplyId;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public MultipartFile getReplyAttachFile() {
		return replyAttachFile;
	}

	public void setReplyAttachFile(MultipartFile replyAttachFile) {
		this.replyAttachFile = replyAttachFile;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getFileGroupId() {
		return fileGroupId;
	}

	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "RequestCreateReplyVO [boardId=" + boardId + ", replyAttachFile=" + replyAttachFile + ", replyContent="
				+ replyContent + ", fileGroupId=" + fileGroupId + ", email=" + email + "]";
	}

}
