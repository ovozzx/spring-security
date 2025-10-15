package com.ktdsuniversity.edu.common.vo;

/**
 * 페이지네이션과 검색을 담당할 공통 VO 
 * 페이지네이션이 필요한 VO에서 상속으로 사용한다.
 */
public abstract class AbstractSearchVO {

	/**
	 * 검색할 페이지 번호 
	 * 0 부터 시작.
	 */
	private int pageNo;

	/**
	 * 한 페이지에 보여줄 항목의 수
	 */
	private int listSize;

	/**
	 * 생성될 페이지의 개수 
	 * 올림(항목의 수 / listSize)
	 */
	private int pageCount;
	
	//// Page Pagination 시작
	/**
	 * 한 페이지 그룹에 노출시킬 페이지의 개수
	 */
	private int pageCountInGroup;
	
	/**
	 * 페이지 그룹의 총 개수
	 * 올림(페이지 수 / pageCountInGroup)
	 */
	private int groupCount;
	
	/**
	 * 현재 페이지의 그룹 번호
	 * 소수점버림(pageNo / pageCountInGroup)
	 */
	private int groupNo;
	
	/**
	 * 현재 페이지 그룹의 시작 페이지 번호
	 * groupNo * pageCountInGroup
	 */
	private int groupStartPageNo;
	
	/**
	 * 현재 페이지 그룹의 마지막 페이지 번호
	 * 1. (groupNo + 1) * pageCountInGroup - 1
	 * 2. groupStartPageNo + pageCountInGroup - 1
	 */
	private int groupEndPageNo;
	
	/**
	 * 현재 페이지 그룹의 다음 그룹이 있는지 여부
	 * groupNo + 1 < groupCount
	 */
	private boolean haveNextPageGroup;
	
	/**
	 * 현재 페이지 그룹의 이전 그룹이 있는지 여부
	 * groupNo > 0
	 */
	private boolean havePrevPageGroup;
	
	/**
	 * 다음 그룹의 시작 페이지 번호
	 * groupEndPageNo + 1
	 */
	private int nextGroupStartPageNo;
	
	/**
	 * 이전 그룹의 시작 페이지 번호
	 * groupStartPageNo - pageCountInGroup
	 */
	private int prevGroupStartPageNo;
	//// Page Pagination 끝

	public AbstractSearchVO() {
		this.listSize = 20;
		this.pageCountInGroup = 10;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 생성할 총 페이지의 개수를 계산한다.
	 * @param listCount: 조회된 게시글의 총 수
	 */
	public void setPageCount(int listCount) {
		this.pageCount = (int) Math.ceil((double) listCount / this.listSize);
	}

}
