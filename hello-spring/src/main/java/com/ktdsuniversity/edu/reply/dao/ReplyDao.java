package com.ktdsuniversity.edu.reply.dao;

import java.util.List;

import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateReplyVO;

public interface ReplyDao {

	int insertNewReply(RequestCreateReplyVO requestCreateReplyVO);

	ReplyVO selectReplyByReplyId(String replyId);

	List<ReplyVO> selectReplyListByBoardId(String boardId);

	int updateReplyRecommendByReplyId(String replyId);

}