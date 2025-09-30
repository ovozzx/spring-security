package com.ktdsuniversity.edu.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.member.vo.RequestMemberLoginVO;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

import jakarta.servlet.http.HttpSession;
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
    
    @GetMapping("/member/login")
    public String viewLoginPage() {
    	return "member/login";
    }
    
    @PostMapping("/member/login")
    public String doMemberLoginAction(
    		@Valid RequestMemberLoginVO requestMemberLoginVO,
    		BindingResult bindingResult,
    		Model model,
    		@RequestParam String nextUrl,
    		HttpSession httpSession) {
    	
    	if (bindingResult.hasErrors()) {
    		model.addAttribute("inputData", requestMemberLoginVO);
    		return "member/login";
    	}
    	
    	MemberVO memberVO = this.memberService.readMember(requestMemberLoginVO);
    	httpSession.setAttribute("__LOGIN_USER__", memberVO);
    	
    	return "redirect:" + nextUrl;
    }
    
    @GetMapping("/member/logout")
    public String doLogoutAction(HttpSession httpSession) {
    	httpSession.invalidate();
    	return "redirect:/list";
    }
    
    @GetMapping("/member/delete-me")
    public String doDeleteMeAction(
    		HttpSession httpSession, 
    		@SessionAttribute("__LOGIN_USER__") MemberVO memberVO) {
    	// 1. 현재 로그인한 사용자의 이메일을 가져온다.
    	// Case 1.
//    	MemberVO memberVO = (MemberVO) httpSession.getAttribute("__LOGIN_USER__");
//    	String email = memberVO.getEmail();
    	String email = memberVO.getEmail();
    	// 2. 현재 로그인한 사용자의 이메일로 MEMBER 테이블의 DEL_YN을 "Y"로 변경한다.
    	boolean updateResult = this.memberService.updateDelYnByEmail(email);
    	
    	// 3. 현재 로그인한 사용자를 로그아웃 시킨다.
    	httpSession.invalidate();
    	
    	// 4. /member/delete-success 로 이동한다.
    	return "redirect:/member/delete-success";
    }
    
    @GetMapping("/member/delete-success")
    public String viewDeleteSuccessPage() {
    	return "member/deletesuccess";
    }
    
}










