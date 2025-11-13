package com.ktdsuniversity.edu.common.security.jwt.web;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.common.security.jwt.JwtProvider;
import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.member.vo.RequestMemberLoginVO;

/**
 * JWT를 이용한 로그인 엔드포인트.
 */
@RestController
public class JwtAuthenticationController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private JwtProvider jwtProvider;
	
	@PostMapping("/auth")
	public AjaxResponse
	          generateToken(@RequestBody RequestMemberLoginVO requestMemberLoginVO){
		// @RequestBody : JSON을 주면 객체로 받겠다
		MemberVO memberVO = this.memberService.readMember(requestMemberLoginVO);
		
		String jwt = this.jwtProvider.generate(Duration.ofDays(30), memberVO); // 30일 동안 유효한 토큰 (jwt)
		
		AjaxResponse jwtResponse = new AjaxResponse();
		
		if(jwt != null) {
			jwtResponse.setBody(jwt);
		}
		else {
			jwtResponse.setError(Map.of("message", "아이디 또는 비밀번호가 일치하지 않습니다."));
		}
		
		jwtResponse.setBody(jwt);
		
		return jwtResponse;
	}
}
