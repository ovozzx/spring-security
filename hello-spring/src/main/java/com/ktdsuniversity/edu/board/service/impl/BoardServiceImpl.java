package com.ktdsuniversity.edu.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.ResponseBoardListVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;
	
	@Override
	public ResponseBoardListVO readBoardList() {
		
		// 게시글의 개수 필요.
		int count = this.boardDao.selectBoardCount();
		
		// 게시글의 목록 필요.
		List<BoardVO> list = this.boardDao.selectBoardList();
		
		// 게시글의 개수 + 게시글의 목록 반환.
		ResponseBoardListVO result = new ResponseBoardListVO();
		result.setCount(count);
		result.setList(list);
		
		return result;
		
	}

	@Override
	public boolean createNewBoard(RequestCreateBoardVO requestCreateBoardVO) {
		// boardDao를 통해서 insert 를 수행시킨다.
		// 그 결과를 반환시킨다.
		return this.boardDao.insertNewBoard(requestCreateBoardVO) > 0;
	}

	@Override
	public BoardVO readBoardOneById(String id) {
		// 1. 게시글의 조회수를 1 증가시킨다.
		/*
		 * UPDATE BOARD
		 *    SET VIEW_CNT = VIEW_CNT + 1
		 *  WHERE ID = ?
		 */
		int updateCount = this.boardDao.updateViewCntById(id);
		if (updateCount == 0) {
			throw new IllegalArgumentException(id + "게시글은 존재하지 않습니다.");
		}
		
		// 2. 게시글의 내용을 조회한다.
		BoardVO board = this.boardDao.selectBoardById(id);
		
		// 3. 게시글의 내용을 반환시킨다.
		return board;
	}

}
