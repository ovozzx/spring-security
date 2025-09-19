package com.ktdsuniversity.edu.board.dao;

import java.util.List;

import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;

/**
 * Database에서 Insert, Select, Update, Delete를 수행 요청하는 인터페이스.
 *             Create, Read,   Update, Delete
 *             CRUD
 */
public interface BoardDao {

	public int selectBoardCount();
	
	public List<BoardVO> selectBoardList();

	public int insertNewBoard(RequestCreateBoardVO requestCreateBoardVO);

	public int updateViewCntById(String id);

	public BoardVO selectBoardById(String id);
	
}
