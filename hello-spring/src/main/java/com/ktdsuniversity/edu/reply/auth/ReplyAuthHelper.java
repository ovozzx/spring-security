package com.ktdsuniversity.edu.reply.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktdsuniversity.edu.common.util.AuthenticationUtil;
import com.ktdsuniversity.edu.reply.dao.ReplyDao;
import com.ktdsuniversity.edu.reply.vo.ReplyVO;

@Component
public class ReplyAuthHelper {
    // url에 있는 변수의 값을 활용해서 게시글을 조회하고
	// 그 게시글을 작성한 사용자의 이메일과 나의 이메일이 같은지 확인한다.
	@Autowired
	private ReplyDao replyDao;
	
	public boolean isResourceOwner(String replyId) {
		ReplyVO replyVO = this.replyDao.selectReplyByReplyId(replyId);
		if(replyVO == null) {
			return false;
		}
		return replyVO.getEmail().equals(AuthenticationUtil.getEmail());
	}

	
}
