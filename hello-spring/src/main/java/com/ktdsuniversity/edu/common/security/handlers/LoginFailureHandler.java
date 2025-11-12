package com.ktdsuniversity.edu.common.security.handlers;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.ktdsuniversity.edu.member.dao.MemberDao;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFailureHandler implements AuthenticationFailureHandler{
	
	private MemberDao memberDao;
	
	public LoginFailureHandler(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		if(exception instanceof BadCredentialsException bce) {
			String email = bce.getMessage(); // provider BadCredentialsException messag에 이메일을 넣음
			this.memberDao.updateLoginFailCountByEmail(email);
			this.memberDao.updateBlockByEmail(email);
		}
		
		// /member/login 페이지가 보이도록 처리.
		// 에러 메세지도 보내줌.
		// 사용자가 보내준 이메일 보내줌.
		
		// CheckSessionInterceptor 내용 복사
		// 로그인 페이지 보여주기.
		// 1. view 찾기
		RequestDispatcher view = 
				request.getRequestDispatcher("/WEB-INF/views/member/login.jsp");
		request.setAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
		request.setAttribute("email", request.getParameter("eamil"));
		// 2. view 보여주기
		view.forward(request, response);
		
		
	}
	
}
