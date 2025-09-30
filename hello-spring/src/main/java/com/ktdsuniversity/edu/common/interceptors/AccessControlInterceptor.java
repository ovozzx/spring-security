package com.ktdsuniversity.edu.common.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AccessControlInterceptor implements HandlerInterceptor {

	// Controller가 실행되기 전에 아래 항목을 체크.
	//  1. 세션이 존재하는지 확인.
	//  2. 세션이 존재한다면 "/list"로 이동.
	// return "redirect:/list";
	// response.sendRedirect("/list");
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginUser = (MemberVO) session.getAttribute("__LOGIN_USER__");
		
		if (loginUser != null) {
			response.sendRedirect("/list");
			return false;
		}
		
		return true;
	}
	
}
