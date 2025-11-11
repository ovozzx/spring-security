package com.ktdsuniversity.edu.common.security.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ktdsuniversity.edu.common.util.SHAEncrypter;

/**
 * 사용자의 아이디(이메일)로 회원의 정보를 조회한 결과
 * 내에서 사용자가 보내준 비밀번호와 암호화된 비밀번호가 일치하는지 검사
 * 
 * 일치하는지 아닌지 여부만 돌려준다.
 * 
 */
@Component
public class SecurityPasswordEncoder implements PasswordEncoder{ // Bean으로 넣어줘야 동작함 => @Component

	public String encode(String rawPassword, String salt) { // 이걸 씀, salt 때문
		return SHAEncrypter.getEncrypt(rawPassword, salt); // static이라서 그냥 부르면 됨
	}
	
	@Override
	public String encode(CharSequence rawPassword) { // 이거 안 씀
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.equals(encodedPassword);
	}

}
