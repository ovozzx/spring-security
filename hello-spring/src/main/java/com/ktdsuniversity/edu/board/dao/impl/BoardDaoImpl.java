package com.ktdsuniversity.edu.board.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestModifyBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestSearchBoardVO;

@Repository
public class BoardDaoImpl 
		extends SqlSessionDaoSupport // MyBatis 포함된 클래스. Database에 연결을 하고 CRUD를 하기 위한 기능이 모여있는 Util Class 
		implements BoardDao {

	private final String NAME_SPACE = "com.ktdsuniversity.edu.board.dao.impl.BoardDaoImpl.";
	
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public int selectBoardCount(RequestSearchBoardVO requestSearchBoardVO) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectBoardCount", requestSearchBoardVO);
	}

	@Override
	public List<BoardVO> selectBoardList(RequestSearchBoardVO requestSearchBoardVO) {
		return super.getSqlSession().selectList(this.NAME_SPACE + "selectBoardList", requestSearchBoardVO);
	}

	@Override
	public int insertNewBoard(RequestCreateBoardVO requestCreateBoardVO) {
		if (requestCreateBoardVO == null) {
			return 0;
		}
		return super.getSqlSession().insert(this.NAME_SPACE + "insertNewBoard", requestCreateBoardVO);
	}

	@Override
	public int updateViewCntById(String id) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateViewCntById", id);
	}

	@Override
	public BoardVO selectBoardById(String id) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectBoardById", id);
	}

	@Override
	public int updateBoardModifyById(RequestModifyBoardVO requestModifyBoardVO) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateBoardModifyById", requestModifyBoardVO);
	}

	@Override
	public int deleteBoardById(String id) {
		return super.getSqlSession().update(this.NAME_SPACE + "deleteBoardById", id);
	}

	@Override
	public List<BoardVO> selectBoardListForExcel() {
		return super.getSqlSession().selectList(this.NAME_SPACE + "selectBoardListForExcel");
	}

}
