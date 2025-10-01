package com.ktdsuniversity.edu.board.vo;

import java.util.stream.Collectors;

import com.ktdsuniversity.edu.file.vo.FileVO;

import io.github.seccoding.excel.annotations.ExcelSheet;
import io.github.seccoding.excel.annotations.Title;

@ExcelSheet("게시글목록")
public class BoardExcelVO {

	@Title("번호")
	private int number;
	
	@Title("아이디")
	private String id;
	
	@Title("제목")
	private String subject;
	
	@Title("작성자 이메일")
	private String email;
	
	@Title("작성자 이름")
	private String name;
	
	@Title("첨부파일")
	private String attachedFile;
	
	@Title("조회수")
	private int viewCnt;
	
	@Title("등록일")
	private String crtDt;
	
	@Title("수정일")
	private String mdfyDt;

	public BoardExcelVO(BoardVO boardVO) {
		this.number = boardVO.getNumber();
		this.id = boardVO.getId();
		this.subject = boardVO.getSubject();
		this.email = boardVO.getEmail();
		this.name = boardVO.getMemberVO().getName();
		this.viewCnt = boardVO.getViewCnt();
		this.crtDt = boardVO.getCrtDt();
		this.mdfyDt = boardVO.getMdfyDt();

		this.attachedFile = "";
		if (boardVO.getFileGroupVO() != null) {
			this.attachedFile = boardVO.getFileGroupVO().getFile().stream().map(FileVO::getFileDisplayName)
					.collect(Collectors.joining(", "));
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

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

	public String getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(String attachedFile) {
		this.attachedFile = attachedFile;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getCrtDt() {
		return crtDt;
	}

	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}

	public String getMdfyDt() {
		return mdfyDt;
	}

	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}

}
