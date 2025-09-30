package com.ktdsuniversity.edu.common.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;

import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckSessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession httpSession = request.getSession();
		MemberVO loginUser = (MemberVO) httpSession.getAttribute("__LOGIN_USER__");
		
		if (loginUser == null) {
			// 로그인 페이지 보여주기.
			// 1. View 찾기
			RequestDispatcher view = 
					request.getRequestDispatcher("/WEB-INF/views/member/login.jsp");
			
			// 2. view 보여주기
			view.forward(request, response);
			
			// Session 이 없으면 Controller를 실행하지 않는다.
			return false;
		}
		
		return true;
	}
	
}
