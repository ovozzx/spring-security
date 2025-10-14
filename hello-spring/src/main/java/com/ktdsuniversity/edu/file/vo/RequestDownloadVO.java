package com.ktdsuniversity.edu.file.vo;

public class RequestDownloadVO {

	private String id;
	private String fileGroupId;
	private String fileId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileGroupId() {
		return fileGroupId;
	}

	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Override
	public String toString() {
		return "RequestDownloadVO [id=" + id + ", fileGroupId=" + fileGroupId + ", fileId=" + fileId + "]";
	}

}
