package com.ktdsuniversity.edu.common.security.oauth.provider;

// ibatis ==> MyBatis의 예전 이름
import org.apache.ibatis.annotations.Mapper;

import com.ktdsuniversity.edu.common.security.oauth.provider.vo.OAuthProviderVO;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

// Mybatis ==> 가상의 클래스 생성
@Mapper // impl 없이 동작 가능하게 함. 맵퍼에 이 파일 경로 적어주기
public interface ProviderDao {
	
	public int selectMemberCountByEmail(String email);
	
	public int insertOAuthMember(RequestRegistMemberVO requestRegistMemberVO);
	
	public int insertOAuthProvider(OAuthProviderVO oAuthProviderVO);
	
	public int selectProviderCountByEmailAndProvider(OAuthProviderVO oAuthProviderVO);
	
}
