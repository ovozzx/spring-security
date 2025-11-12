package com.ktdsuniversity.edu.board.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.common.util.AuthenticationUtil;

@Component // DAO가 필요하니 등록
public class BoardAuthHelper {
	// boardAuthHelper
	
	// url에 있는 변수의 값을 활용해서 게시글을 조회하고
	// 그 게시글을 작성한 사용자의 이메일과 나의 이메일이 같은지 확인한다.
	@Autowired
	private BoardDao boardDao;
	
	public boolean isResourceOwner(String boardId) {
		BoardVO boardVO = this.boardDao.selectBoardById(boardId);
		if(boardVO == null) {
			return false;
		}
		return boardVO.getEmail().equals(AuthenticationUtil.getEmail());
	}
	
}
