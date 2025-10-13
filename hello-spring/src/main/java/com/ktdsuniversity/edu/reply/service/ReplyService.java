package com.ktdsuniversity.edu.reply.service;

import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateReplyVO;

public interface ReplyService {

	ReplyVO createReply(RequestCreateReplyVO requestCreateReplyVO);

}