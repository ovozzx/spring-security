package com.ktdsuniversity.edu.common.security.jwt;

import java.security.SignatureException;
import java.time.Duration;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktdsuniversity.edu.common.security.providers.SecurityAuthenticationProvider;
import com.ktdsuniversity.edu.member.vo.MemberVO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JWT 생성 / 검증 역할 
 */
@Component
public class JwtProvider {

    private final SecurityAuthenticationProvider securityAuthenticationProvider;
	
	// yml 값 가져오기
	@Value("${app.jwt.issure}")
	private String issure;
	
	@Value("${app.jwt.secret-key}")
	private String secretKey;

    JwtProvider(SecurityAuthenticationProvider securityAuthenticationProvider) {
        this.securityAuthenticationProvider = securityAuthenticationProvider;
    }
	
	public String generate(Duration duration, MemberVO memberVO) {
		
		if(memberVO == null) {
			return null;
		}
		
		// 유효기간 설정.
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + duration.toMillis());
		
		// 암호화 키 생성.
		SecretKey jwtKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());
		
		// JWT 생성 : 토큰 만들 때 순서 중요
		String jwt = Jwts.builder()
					     .issuer(this.issure)
					     .subject("hello-spring")
					     .claim("user", memberVO)
					     .issuedAt(now)
					     .expiration(expireDate)
					     .signWith(jwtKey)
					     .compact()
					     ;
		
		// JWT 반환
		return jwt;
	}
	
	public MemberVO verify(String jwt) throws JsonProcessingException, SignatureException{
		
		// JWT 복호화를 위한 키 생성.
		SecretKey jwtKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());
		
		// JWT를 복호화
		Object userClaim = Jwts.parser()
							    .verifyWith(jwtKey) // 정해진 키로 복호화가 실패한다면 토큰이 변조되었음을 의미 ==> Exception 발생!
							    .requireIssuer(this.issure) // issuer가 ktds-university 아니라면 토큰이 변조되었음을 의미 ==> Exception 발생 
					            .requireSubject("hello-spring") // subject가 hello-spring이 아니라면 토큰이 변조되었음을 의미 ==> Exception 발생
					            .build() // 복호화 진행 시작
					            .parseSignedClaims(jwt)
					            .getPayload()
					            .get("user")
					            ;
		
		// claims (user) 추출 ==> JSON
		// 객체 간의 복제 또는 변환을 담당하는 클래스. 
		// Java Copy (Shallow, Deep) ==> Deep Copy 가능
		ObjectMapper om = new ObjectMapper();
		String memberJson = om.writeValueAsString(userClaim);
		
		// JSON ==> MemberVO로 변환
		MemberVO memberVO = om.readValue(memberJson, MemberVO.class);
		
		// MemberVO 반환
		return memberVO;
	}

}
