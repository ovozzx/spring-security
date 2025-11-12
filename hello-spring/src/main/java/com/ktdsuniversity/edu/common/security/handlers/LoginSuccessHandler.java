package com.ktdsuniversity.edu.common.security.handlers;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 로그인이 성공했을 때 처리할 객체
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler{

	private MemberDao memberDao; // 성공 카운트 db에 업데이트 해야하기 때문에 필요
	
	public LoginSuccessHandler(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 로그인에 성공한 회원의 아이디 (이메일)
		MemberVO authMember = (MemberVO) authentication.getPrincipal();
		String email = authMember.getEmail();
		// 로그인 기록
		this.memberDao.updateLoginSuccessByEmail(email);
		String nextUrl = request.getParameter("nextUrl");
		
		// nextUrl로 이동
		response.sendRedirect(nextUrl);
		
	}
	
}
