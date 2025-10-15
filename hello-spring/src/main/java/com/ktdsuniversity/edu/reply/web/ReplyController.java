package com.ktdsuniversity.edu.reply.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.common.exceptions.AjaxException;
import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.reply.service.ReplyService;
import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateOrUpdateReplyVO;

import jakarta.validation.Valid;

@RestController
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping("/reply/{boardId}")
    public AjaxResponse doReadReplyListAction(
    		@PathVariable String boardId) {
    	List<ReplyVO> replyList = this.replyService.readReplyListByBoardId(boardId);
    	
    	AjaxResponse replyListResponse = new AjaxResponse();
    	replyListResponse.setBody(replyList);
    	return replyListResponse;
    }
    
    @PostMapping("/reply/{boardId}")
    public AjaxResponse doCreateOrUpdateReplyAction(
    		@Valid RequestCreateOrUpdateReplyVO requestCreateOrUpdateReplyVO,
    		BindingResult bindingResult,
    		@PathVariable String boardId,
    		@SessionAttribute("__LOGIN_USER__") MemberVO memberVO) {
    	
    	if (bindingResult.hasErrors()) {
    		throw new AjaxException(null, HttpStatus.BAD_REQUEST, bindingResult.getFieldErrors());
    	}
    	
    	requestCreateOrUpdateReplyVO.setBoardId(boardId);
    	requestCreateOrUpdateReplyVO.setEmail(memberVO.getEmail());
    	
    	ReplyVO newReply = this.replyService.createOrUpdateReply(requestCreateOrUpdateReplyVO);
    	
    	AjaxResponse replyResponse = new AjaxResponse();
    	replyResponse.setBody(newReply);
    	
    	return replyResponse;
    }
    
    @GetMapping("/reply/{replyId}/recommend")
    public AjaxResponse doRecommendReplyAction(@PathVariable String replyId) {
    	
    	int latestRecommendCount = 
    			this.replyService.updateReplyRecommendByReplyId(replyId);
    	
    	AjaxResponse recommendResponse = new AjaxResponse();
    	recommendResponse.setBody(latestRecommendCount);
    	return recommendResponse;
    }
    
    @GetMapping("/reply/{replyId}/delete")
    public AjaxResponse doRemoveReplyAction(@PathVariable String replyId) {
    	
    	boolean deleteResult = 
    			this.replyService.deleteReplyByReplyId(replyId);
    	
    	AjaxResponse recommendResponse = new AjaxResponse();
    	recommendResponse.setBody(deleteResult);
    	return recommendResponse;
    }
}




