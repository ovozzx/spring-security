package com.ktdsuniversity.edu.reply.service;

import java.util.List;

import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateOrUpdateReplyVO;

public interface ReplyService {

	ReplyVO createOrUpdateReply(RequestCreateOrUpdateReplyVO requestCreateReplyVO);

	List<ReplyVO> readReplyListByBoardId(String boardId);

	int updateReplyRecommendByReplyId(String replyId);

	boolean deleteReplyByReplyId(String replyId);

}