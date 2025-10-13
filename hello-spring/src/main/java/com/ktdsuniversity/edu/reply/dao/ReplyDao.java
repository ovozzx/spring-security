package com.ktdsuniversity.edu.reply.dao;

import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateReplyVO;

public interface ReplyDao {

	int insertNewReply(RequestCreateReplyVO requestCreateReplyVO);

	ReplyVO selectReplyByReplyId(String replyId);

}