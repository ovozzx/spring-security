package com.ktdsuniversity.edu.board.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;

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
	public int selectBoardCount() {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectBoardCount");
	}

	@Override
	public List<BoardVO> selectBoardList() {
		return super.getSqlSession().selectList(this.NAME_SPACE + "selectBoardList");
	}

	@Override
	public int insertNewBoard(RequestCreateBoardVO requestCreateBoardVO) {
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

}
