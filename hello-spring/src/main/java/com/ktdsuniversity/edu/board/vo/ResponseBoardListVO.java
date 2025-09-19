package com.ktdsuniversity.edu.board.vo;

import java.util.List;

public class ResponseBoardListVO {

	private int count;
	
	private List<BoardVO> list;

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<BoardVO> getList() {
		return this.list;
	}

	public void setList(List<BoardVO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "ResponseBoardListVO [count=" + this.count + ", list=" + this.list + "]";
	}
	
	
	
}
