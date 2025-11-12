package com.ktdsuniversity.edu.common.security.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ktdsuniversity.edu.member.vo.MemberVO;

/**
 * Spring Security 의 사용자 인증 객체
 */
public class SecurityUser implements UserDetails{ // 인터페이스 가져오기

	private static final long serialVersionUID = 5116323134247933585L; // extends Serializable
	// UserDetails이 serial 관련 상속받는 게 있어서 써야함, 값이 같으면 복제 다르면 복제를 안한다? 클러스터링 환경에서 사용 (stand alone이면 노상관) 

	// 사용자의 정보가 존재하지 않는다 
	private MemberVO memberVO;

	public SecurityUser(MemberVO memberVO) {
		this.memberVO = memberVO;
	}

	public MemberVO getMemberVO() {
		return this.memberVO;
	}

	/* unimplemented 함수 구현, 필요에 따라 다른 거 오버라이딩 가능*/
	// 처리 하지 않은 영역
	// 사용자가 할 수 있는 역할에 대해 작성
	// 일반적으로 DB에서 권한 정보를 관리
	// DB에서 조회된 정보로 사용자의 권한을 부여
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.memberVO.getRoles()
				            .stream()
						    .map(SimpleGrantedAuthority::new)
						    .toList();
		//return List.of(new SimpleGrantedAuthority("ROLE_ADMIN")
		//		     , new SimpleGrantedAuthority("ROLE_USER")); // 이 유저는 관리자이면서 사용자라는 뜻!
	}

	// Spring Security는 인증을 위해 필요하 정보로 Password, ID만 필요로 함
	// 인증 정보에서 Password, ID를 추출할 수 있도록 해야 한다.
	@Override
	public String getPassword() {
		// DB에서 조회한 사용자의 암호화된 비밀번호
		return this.memberVO.getPassword();
	}

	@Override
	public String getUsername() {
		// DB에서 조회한 사용자의 아이디 (이름이 아님)
		return this.memberVO.getEmail();
	} 

	// UserDetails > isAccountLock 함수 있음
	@Override
	public boolean isAccountNonLocked() { // 잠기지 않았는지 확인
		return this.memberVO.getBlockYn().equals("N");
	}
	
	
}
