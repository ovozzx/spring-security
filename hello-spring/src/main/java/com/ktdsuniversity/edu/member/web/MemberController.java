package com.ktdsuniversity.edu.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

import jakarta.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @GetMapping("/member/regist")
    public String viewMemberRegistPage() {
    	return "member/regist";
    }
    
    @PostMapping("/member/regist")
    public String doMemberRegistAction(
    		@Valid RequestRegistMemberVO requestRegistMemberVO,
    		BindingResult bindingResult,
    		Model model) {
    	
    	if (bindingResult.hasErrors()) {
    		model.addAttribute("registData", requestRegistMemberVO);
    		return "member/regist";
    	}
    	
    	// Password 일치검사
    	if ( ! requestRegistMemberVO.getPassword()
    				.equals( requestRegistMemberVO.getPasswordConfirm() ) ) {
    		model.addAttribute("registData", requestRegistMemberVO);
    		model.addAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
    		return "member/regist";
    	}
    	
    	boolean createResult = this.memberService.createNewMember(requestRegistMemberVO);
    	
    	return "redirect:/member/login";
    }
    
    @ResponseBody
    @GetMapping("/member/regist/check")
    public AjaxResponse doDuplicateEmailCheckAction(@RequestParam String email) {
    	int count = this.memberService.readMemberCountByEmail(email);
    	
    	AjaxResponse ajaxResponse = new AjaxResponse();
    	ajaxResponse.setBody(count);
    	return ajaxResponse;
    }
    
}










