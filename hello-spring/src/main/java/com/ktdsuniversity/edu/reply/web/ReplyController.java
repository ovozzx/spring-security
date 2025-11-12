package com.ktdsuniversity.edu.reply.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.common.exceptions.AjaxException;
import com.ktdsuniversity.edu.common.util.AuthenticationUtil;
import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.reply.service.ReplyService;
import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateOrUpdateReplyVO;

import jakarta.validation.Valid;

@PreAuthorize("isAuthenticated()") // 동일한 규칙이면 이렇게 가능. 하나하나 달 필요 없음
@RestController
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping("/reply/{boardId}")
    public AjaxResponse doReadReplyListAction(
    		@PathVariable String boardId, Authentication authentication) {
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
    		Authentication authentication) {
    	
    	if (bindingResult.hasErrors()) {
    		throw new AjaxException(null, HttpStatus.BAD_REQUEST, bindingResult.getFieldErrors());
    	}
    	
    	requestCreateOrUpdateReplyVO.setBoardId(boardId);
    	requestCreateOrUpdateReplyVO.setEmail(AuthenticationUtil.getEmail());
    	
    	ReplyVO newReply = this.replyService.createOrUpdateReply(requestCreateOrUpdateReplyVO);
    	
    	AjaxResponse replyResponse = new AjaxResponse();
    	replyResponse.setBody(newReply);
    	
    	return replyResponse;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or !@replyAuthHelper.isResourceOwner(#replyId)") 
    @GetMapping("/reply/{replyId}/recommend") // 내가 쓴 거는 추천 못함
    public AjaxResponse doRecommendReplyAction(@PathVariable String replyId, Authentication authentication) {
    	
    	int latestRecommendCount = 
    			this.replyService.updateReplyRecommendByReplyId(replyId);
    	
    	AjaxResponse recommendResponse = new AjaxResponse();
    	recommendResponse.setBody(latestRecommendCount);
    	return recommendResponse;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') or @replyAuthHelper.isResourceOwner(#replyId)") 
    @GetMapping("/reply/{replyId}/delete") // 관리자 or 내가 쓴 글
    public AjaxResponse doRemoveReplyAction(@PathVariable String replyId) {
    	
    	boolean deleteResult = 
    			this.replyService.deleteReplyByReplyId(replyId);
    	
    	AjaxResponse recommendResponse = new AjaxResponse();
    	recommendResponse.setBody(deleteResult);
    	return recommendResponse;
    }
}




