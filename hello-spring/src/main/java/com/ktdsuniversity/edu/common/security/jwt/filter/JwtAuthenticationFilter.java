package com.ktdsuniversity.edu.common.security.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.ktdsuniversity.edu.common.security.jwt.JwtProvider;
import com.ktdsuniversity.edu.common.security.user.SecurityUser;
import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.common.web.CustomErrorController;
import com.ktdsuniversity.edu.member.vo.MemberVO;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 사용자가 요청을 할 때마다 JWT 검증을 수행하고
 * 인증 객체를 생성하는 역할.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final CustomErrorController customErrorController;
	
	@Autowired
	private JwtProvider jwtProvider;

    JwtAuthenticationFilter(CustomErrorController customErrorController) {
        this.customErrorController = customErrorController;
    }
	
	// Request와 Response가 발생할 때마다 실행되는 메소드 
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		// 인증을 끝내고 그다음 필터 동작
		// JWT 인증 시작.
		// 모든 end point를 대상으로 인증 => /api로 된 것만 (jwt로 인증하는 애들)
		// request를 한 URL 확인
		// /list => 인증 대상 X, /api/v1/list ==> 인증 대상 O
		String requestURL = request.getServletPath();
		if(requestURL.startsWith("/api/")) { // 인증 대상
			// RequestHeader에 있는 Authorization의 값을 추출 ==> JWT
			String jwt = request.getHeader("Authorization");
			
			// JWT 가 존재한다
			// JWT를 검증 / 복호화 ==> MemberVO
			if(jwt != null && jwt.length() > 0) {
				try {
					
					MemberVO memberVO = this.jwtProvider.verify(jwt);
					
					// MemberVO ==> SecurityContext에 등록.
					SecurityUser securityUser = new SecurityUser(memberVO);
					
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(
									memberVO, null, securityUser.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
					
				} catch(MalformedJwtException | SignatureException mje){ // 변조 예외처리
					AjaxResponse errorResponse = new AjaxResponse();
					errorResponse.setError(Map.of("message", "토큰 변조가 감지되었습니다."));
					
					Gson gson = new Gson();
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json"); // 컨트롤러가 아닌 영역에서 JSON 보내는 방법
					
					PrintWriter out = response.getWriter();
					out.write(gson.toJson(errorResponse));
					out.flush();
					
					return;
					
				} catch(ExpiredJwtException eje){ // 기간 만료 예외처리
					AjaxResponse errorResponse = new AjaxResponse();
					errorResponse.setError(Map.of("message", "인증이 만료되었습니다."));
					
					Gson gson = new Gson();
					
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json"); // 컨트롤러가 아닌 영역에서 JSON 보내는 방법
					
					PrintWriter out = response.getWriter();
					out.write(gson.toJson(errorResponse));
					out.flush();
					
					return;
				}
				
			}
			
		}
	
		filterChain.doFilter(request, response);
		
	}
}
