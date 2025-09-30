package com.ktdsuniversity.edu.member.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

@Repository
public class MemberDaoImpl extends SqlSessionDaoSupport implements MemberDao {

    private final String NAME_SPACE = "com.ktdsuniversity.edu.member.dao.impl.MemberDaoImpl.";

    @Autowired
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

	@Override
	public int insertNewMember(RequestRegistMemberVO requestRegistMemberVO) {
		return super.getSqlSession().insert(this.NAME_SPACE + "insertNewMember", requestRegistMemberVO);
	}

	@Override
	public int selectMemberCountByEmail(String email) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectMemberCountByEmail", email);
	}

	@Override
	public MemberVO selectMemberByEmail(String email) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectMemberByEmail", email);
	}

	@Override
	public int updateLoginFailCountByEmail(String email) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateLoginFailCountByEmail", email);
	}

	@Override
	public int updateBlockByEmail(String email) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateBlockByEmail", email);
	}

	@Override
	public int updateLoginSuccessByEmail(String email) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateLoginSuccessByEmail", email);
	}

	@Override
	public int selectUnblockMemberByEmail(String email) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectUnblockMemberByEmail", email);
	}

	@Override
	public int updateDelYnByEmail(String email) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateDelYnByEmail", email);
	}


}






