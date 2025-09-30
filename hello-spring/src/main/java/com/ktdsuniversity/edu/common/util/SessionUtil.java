package com.ktdsuniversity.edu.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

	public static MemberVO getLoginObject() {
		ServletRequestAttributes requestAttributes = 
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession httpSession = request.getSession();
		
		MemberVO loginUser = (MemberVO) httpSession.getAttribute("__LOGIN_USER__");
		return loginUser;
	}
	
}
