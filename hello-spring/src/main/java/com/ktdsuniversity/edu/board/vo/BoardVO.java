package com.ktdsuniversity.edu.board.vo;

import com.ktdsuniversity.edu.file.vo.FileGroupVO;
import com.ktdsuniversity.edu.member.vo.MemberVO;

public class BoardVO {

	private int number;
	private String id;
	private String subject;
	private String content;
	private String email;
	private int viewCnt;
	private String crtDt;
	private String mdfyDt;
	private String delYn;

	private MemberVO memberVO;
	private FileGroupVO fileGroupVO;

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
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

	public String getDelYn() {
		return this.delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	public FileGroupVO getFileGroupVO() {
		return this.fileGroupVO;
	}

	public void setFileGroupVO(FileGroupVO fileGroupVO) {
		this.fileGroupVO = fileGroupVO;
	}

	@Override
	public String toString() {
		return "BoardVO [number=" + number + ", id=" + id + ", subject=" + subject + ", content=" + content + ", email="
				+ email + ", viewCnt=" + viewCnt + ", crtDt=" + crtDt + ", mdfyDt=" + mdfyDt + ", delYn=" + delYn
				+ ", memberVO=" + memberVO + ", fileGroupVO=" + fileGroupVO + "]";
	}

}