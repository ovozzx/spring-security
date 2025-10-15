package com.ktdsuniversity.edu.board.dao;

import java.util.List;

import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestModifyBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestSearchBoardVO;

/**
 * Database에서 Insert, Select, Update, Delete를 수행 요청하는 인터페이스.
 *             Create, Read,   Update, Delete
 *             CRUD
 */
public interface BoardDao {

	public int selectBoardCount(RequestSearchBoardVO requestSearchBoardVO);
	
	public List<BoardVO> selectBoardList(RequestSearchBoardVO requestSearchBoardVO);

	public int insertNewBoard(RequestCreateBoardVO requestCreateBoardVO);

	public int updateViewCntById(String id);

	public BoardVO selectBoardById(String id);

	public int updateBoardModifyById(RequestModifyBoardVO requestModifyBoardVO);

	public int deleteBoardById(String id);

	public List<BoardVO> selectBoardListForExcel();
	
}
