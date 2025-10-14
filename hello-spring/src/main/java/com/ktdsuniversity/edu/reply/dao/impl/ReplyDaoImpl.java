package com.ktdsuniversity.edu.reply.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.reply.dao.ReplyDao;
import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateOrUpdateReplyVO;

@Repository
public class ReplyDaoImpl extends SqlSessionDaoSupport implements ReplyDao {

    private final String NAME_SPACE = "com.ktdsuniversity.edu.reply.dao.impl.ReplyDaoImpl.";

    @Autowired
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

	@Override
	public int insertNewReply(RequestCreateOrUpdateReplyVO requestCreateReplyVO) {
		return super.getSqlSession().insert(this.NAME_SPACE + "insertNewReply", requestCreateReplyVO);
	}

	@Override
	public ReplyVO selectReplyByReplyId(String replyId) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectReplyByReplyId", replyId);
	}

	@Override
	public List<ReplyVO> selectReplyListByBoardId(String boardId) {
		return super.getSqlSession().selectList(this.NAME_SPACE + "selectReplyListByBoardId", boardId);
	}
	
	@Override
	public int updateReplyRecommendByReplyId(String replyId) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateReplyRecommendByReplyId", replyId);
	}

	@Override
	public int deleteReplyByReplyId(String replyId) {
		return super.getSqlSession().update(this.NAME_SPACE + "deleteReplyByReplyId", replyId);
	}
	
	@Override
	public int updateReply(RequestCreateOrUpdateReplyVO requestCreateOrUpdateReplyVO) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateReply", requestCreateOrUpdateReplyVO);
	}

}