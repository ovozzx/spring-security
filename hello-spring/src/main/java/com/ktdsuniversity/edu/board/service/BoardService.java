package com.ktdsuniversity.edu.board.service;

import java.util.List;

import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestModifyBoardVO;
import com.ktdsuniversity.edu.board.vo.ResponseBoardListVO;

public interface BoardService {

	public ResponseBoardListVO readBoardList();

	public boolean createNewBoard(RequestCreateBoardVO requestCreateBoardVO);

	public BoardVO readBoardOneById(String id, boolean doIncreaseViewCount);

	public boolean updateBoardModifyById(RequestModifyBoardVO requestModifyBoardVO);

	public boolean deleteBoardById(String id);

	public List<BoardVO> readAllBoardListForExcel();
	
}
