package com.ktdsuniversity.edu.reply.vo;

import com.ktdsuniversity.edu.file.vo.FileGroupVO;

/**
 * @TableName REPLY
 * @TableComment null
 */
public class ReplyVO {

	/**
	 * @ColumnName REPLY_ID
	 * @ColumnType VARCHAR2(20)
	 * @ColumnComment null
	 */
	private String replyId;

	/**
	 * @ColumnName BOARD_ID
	 * @ColumnType VARCHAR2(20)
	 * @ColumnComment null
	 */
	private String boardId;

	/**
	 * @ColumnName EMAIL
	 * @ColumnType VARCHAR2(100)
	 * @ColumnComment null
	 */
	private String email;

	/**
	 * @ColumnName CONTENT
	 * @ColumnType CLOB
	 * @ColumnComment null
	 */
	private String content;

	/**
	 * @ColumnName CRT_DT
	 * @ColumnType DATE
	 * @ColumnComment null
	 */
	private String crtDt;

	/**
	 * @ColumnName MDFY_DT
	 * @ColumnType DATE
	 * @ColumnComment null
	 */
	private String mdfyDt;

	/**
	 * @ColumnName RECOMMEND_CNT
	 * @ColumnType NUMBER(36, 0)
	 * @ColumnComment null
	 */
	private int recommendCnt;

	/**
	 * @ColumnName PARENT_REPLY_ID
	 * @ColumnType VARCHAR2(20)
	 * @ColumnComment null
	 */
	private String parentReplyId;

	/**
	 * @ColumnName DEL_YN
	 * @ColumnType CHAR(1)
	 * @ColumnComment null
	 */
	private String delYn;

	/**
	 * @ColumnName FILE_GROUP_ID
	 * @ColumnType VARCHAR2(20)
	 * @ColumnComment null
	 */
	private String fileGroupId;

	private FileGroupVO fileGroupVO;

	public String getReplyId() {
		return this.replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getBoardId() {
		return this.boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
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

	public String getCrtDt() {
		return this.crtDt;
	}

	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}

	public String getMdfyDt() {
		return this.mdfyDt;
	}

	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}

	public int getRecommendCnt() {
		return this.recommendCnt;
	}

	public void setRecommendCnt(int recommendCnt) {
		this.recommendCnt = recommendCnt;
	}

	public String getParentReplyId() {
		return this.parentReplyId;
	}

	public void setParentReplyId(String parentReplyId) {
		this.parentReplyId = parentReplyId;
	}

	public String getDelYn() {
		return this.delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getFileGroupId() {
		return this.fileGroupId;
	}

	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}

	public FileGroupVO getFileGroupVO() {
		return fileGroupVO;
	}

	public void setFileGroupVO(FileGroupVO fileGroupVO) {
		this.fileGroupVO = fileGroupVO;
	}

	@Override
	public String toString() {
		return "ReplyVO(replyId: " + replyId + ", boardId: " + boardId + ", email: " + email + ", content: " + content
				+ ", crtDt: " + crtDt + ", mdfyDt: " + mdfyDt + ", recommendCnt: " + recommendCnt + ", parentReplyId: "
				+ parentReplyId + ", delYn: " + delYn + ", fileGroupId: " + fileGroupId + ", )";
	}
}