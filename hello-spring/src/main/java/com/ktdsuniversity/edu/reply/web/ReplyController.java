package com.ktdsuniversity.edu.reply.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.reply.service.ReplyService;
import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateOrUpdateReplyVO;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping("/reply/{boardId}")
    @ResponseBody 
    public AjaxResponse doReadReplyListAction(
    		@PathVariable String boardId) {
    	List<ReplyVO> replyList = this.replyService.readReplyListByBoardId(boardId);
    	
    	AjaxResponse replyListResponse = new AjaxResponse();
    	replyListResponse.setBody(replyList);
    	return replyListResponse;
    }
    
    @PostMapping("/reply/{boardId}")
    @ResponseBody
    public AjaxResponse doCreateOrUpdateReplyAction(
    		RequestCreateOrUpdateReplyVO requestCreateOrUpdateReplyVO,
    		@PathVariable String boardId,
    		@SessionAttribute("__LOGIN_USER__") MemberVO memberVO) {
    	
    	requestCreateOrUpdateReplyVO.setBoardId(boardId);
    	requestCreateOrUpdateReplyVO.setEmail(memberVO.getEmail());
    	
    	ReplyVO newReply = this.replyService.createOrUpdateReply(requestCreateOrUpdateReplyVO);
    	
    	AjaxResponse replyResponse = new AjaxResponse();
    	replyResponse.setBody(newReply);
    	
    	return replyResponse;
    }
    
    @GetMapping("/reply/{replyId}/recommend")
    @ResponseBody
    public AjaxResponse doRecommendReplyAction(@PathVariable String replyId) {
    	
    	int latestRecommendCount = 
    			this.replyService.updateReplyRecommendByReplyId(replyId);
    	
    	AjaxResponse recommendResponse = new AjaxResponse();
    	recommendResponse.setBody(latestRecommendCount);
    	return recommendResponse;
    }
    
    @GetMapping("/reply/{replyId}/delete")
    @ResponseBody
    public AjaxResponse doRemoveReplyAction(@PathVariable String replyId) {
    	
    	boolean deleteResult = 
    			this.replyService.deleteReplyByReplyId(replyId);
    	
    	AjaxResponse recommendResponse = new AjaxResponse();
    	recommendResponse.setBody(deleteResult);
    	return recommendResponse;
    }
}




