package com.ktdsuniversity.edu.common.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ktdsuniversity.edu.common.security.user.SecurityUser;
import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.vo.MemberVO;

/**
 * Spring Security가 사용자를 인증하려 할 때,
 * 사용자가 보내준 아이디로 DB에서 사용자의 정보를 조회하는 역할.
 * 
 * 조회 결과가 null인 경우는 예외를 보내 인증할 수 없음을 알려준다.
 */
@Component
public class SecurityUserDetailsService implements UserDetailsService{

	@Autowired
	private MemberDao memberDao; // @Autowired 닮에 따라 이 클래스도 Bean에 들어가야 함 => @Component (C/S/R에 해당하지 않으므로)
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // ID로 사용자 정보 조회
		
		MemberVO memberVO = this.memberDao.selectMemberByEmail(username);
		
		if(memberVO == null) {
			throw new UsernameNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		// 반환 타입 UserDetails라서 이를 implements 한 SecurityUser로 반환하면 됨
		return new SecurityUser(memberVO);
	}

}
