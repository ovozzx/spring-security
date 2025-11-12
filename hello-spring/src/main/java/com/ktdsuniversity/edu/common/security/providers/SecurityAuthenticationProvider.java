package com.ktdsuniversity.edu.common.security.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ktdsuniversity.edu.common.security.services.SecurityPasswordEncoder;
import com.ktdsuniversity.edu.common.security.user.SecurityUser;

/**
 * 사용자가 보내준 ID와 비밀번호로 인증을 수행하고
 * 인증 결과가 성공이라면, 인증 객체를 만들어 SecurityContext에 보관시키고
 * 그렇지 않다면, 사용자에게 예외를 보내준다.
 */
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider{

    private final SecurityPasswordEncoder securityPasswordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

    SecurityAuthenticationProvider(SecurityPasswordEncoder securityPasswordEncoder) {
        this.securityPasswordEncoder = securityPasswordEncoder;
    }
	
	// 인증을 수행한다
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// id
		// pw : credential 
		
		// 사용자가 보내준 아이디
		String email = authentication.getName();
		
		// 사용자가 보내준 비밀번호
		String password = authentication.getCredentials().toString(); 
		// 패스워드인데 왜 객체로 보내주는지 : 비밀번호만 있는 게 아님 지문 등..
		
		// 아이디로 DB에서 조회.
		// SecurityUser	UserDetails
		// SecurityUser is a UserDetails
		// UserDetails is not a SecurityUser;
		SecurityUser securityUser = (SecurityUser) this.userDetailsService.loadUserByUsername(email);
		
		// 블럭 여부 Y일 때 예외 던지기
		// UserDetails > isAccountLock 함수 있음
		if(!securityUser.isAccountNonLocked()){ // false이면 블럭되어있는 거
			throw new LockedException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		// 조회한 비번과 사용자가 보내준 비번이 일치하는지 검사
		// 1. 사용자가 보내준 비밀번호를 암호화.
		String encryptedPassword = ((SecurityPasswordEncoder) (this.passwordEncoder))
				                        .encode(password, securityUser.getMemberVO().getSalt()); // securityPasswordEncoder로 캐스팅 해줘야 encode 함수 사용 가능 
		
		// 2. 비교
		boolean matches = this.passwordEncoder.matches(encryptedPassword, securityUser.getPassword());
		
		// 비밀번호가 일치할 경우는 인증 객체 만들어서 Security Context에 보관.
		if(matches) {
			return new UsernamePasswordAuthenticationToken(securityUser.getMemberVO(), 
													       encryptedPassword, 
													       securityUser.getAuthorities()); // 이게 인증 객체
		}
		
		// 그렇지 않으면 예외를 던짐
		throw new BadCredentialsException(email); // 부모가 AuthenticationException
	}

	@Override
	public boolean supports(Class<?> authentication) { // 어떤 타입으로 인증할 것인지
		return authentication.equals(UsernamePasswordAuthenticationToken.class); // 인증 클래스가 이 타입이 맞는지
	}

}
