package com.ktdsuniversity.edu.reply.dao;

import java.util.List;

import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateOrUpdateReplyVO;

public interface ReplyDao {

	int insertNewReply(RequestCreateOrUpdateReplyVO requestCreateReplyVO);

	ReplyVO selectReplyByReplyId(String replyId);

	List<ReplyVO> selectReplyListByBoardId(String boardId);

	int updateReplyRecommendByReplyId(String replyId);

	int deleteReplyByReplyId(String replyId);

	int updateReply(RequestCreateOrUpdateReplyVO requestCreateOrUpdateReplyVO);

}