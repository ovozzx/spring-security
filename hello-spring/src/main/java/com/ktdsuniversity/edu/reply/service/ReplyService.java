package com.ktdsuniversity.edu.reply.service;

import java.util.List;

import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateReplyVO;

public interface ReplyService {

	ReplyVO createReply(RequestCreateReplyVO requestCreateReplyVO);

	List<ReplyVO> readReplyListByBoardId(String boardId);

	int updateReplyRecommendByReplyId(String replyId);

}