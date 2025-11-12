package com.ktdsuniversity.edu.common.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ktdsuniversity.edu.member.vo.MemberVO;

public class AuthenticationUtil { // Authentication도 유틸로 사용 가능

	public static String getEmail() {
		MemberVO authMember = (MemberVO) SecurityContextHolder.getContext()
															  .getAuthentication()
															  .getPrincipal();

		return authMember.getEmail(); 
		// 컨트롤러에 적었던 Authentication를 가져옴 => getName 
		
	}
	
	
}
