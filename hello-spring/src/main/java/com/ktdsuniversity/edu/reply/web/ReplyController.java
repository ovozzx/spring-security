package com.ktdsuniversity.edu.reply.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.reply.service.ReplyService;
import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateReplyVO;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping("/reply/{boardId}")
    @ResponseBody
    public AjaxResponse doCreateReplyAction(
    		RequestCreateReplyVO requestCreateReplyVO,
    		@PathVariable String boardId,
    		@SessionAttribute("__LOGIN_USER__") MemberVO memberVO) {
    	
    	requestCreateReplyVO.setBoardId(boardId);
    	requestCreateReplyVO.setEmail(memberVO.getEmail());
    	
    	ReplyVO newReply = this.replyService.createReply(requestCreateReplyVO);
    	
    	AjaxResponse replyResponse = new AjaxResponse();
    	replyResponse.setBody(newReply);
    	
    	return replyResponse;
    }
    
}




