package com.ktdsuniversity.edu.common.security.oauth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.ktdsuniversity.edu.common.security.oauth.provider.ProviderDao;
import com.ktdsuniversity.edu.common.security.oauth.provider.user.SecurityOAuth2UserInfo;
import com.ktdsuniversity.edu.common.security.oauth.provider.user.authenticate.SecurityOAuthUser;
import com.ktdsuniversity.edu.common.security.oauth.provider.user.naver.NaverOAuth2UserInfo;
import com.ktdsuniversity.edu.common.security.oauth.provider.vo.OAuthProviderVO;
import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

@Component
public class SecurityOAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

	@Autowired
	private ProviderDao providerDao;
	
	@Autowired
	private MemberDao memberDao;
	
	// OAuth 요청
	// Login -> Naver Login 버튼 클릭 -> Naver의 인증 절차 -> 약관/정보제공 동의 -> redirect-uri 정보를 제공 받음
	// -> authorization-uri -> 로그인 사용자 인증 -> token-uri -> 토큰 발급 
	// -> user-info-uri + token -> 사용자의 정보를 가져온다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		// 기본 OAuth 인증 서비스 호출.
		OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultService =
				new DefaultOAuth2UserService();
		
		OAuth2User oAuth2User = defaultService.loadUser(userRequest); // 위 절차 수행해 줌
		
		// 사용자가 어떤 OAuth 서비스를 이용했는가?
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
		// 사용자가 Naver OAuth로 로그인을 했다면
		// Naver를 통해 전달된 사용자의 정보를 추출한다.
		SecurityOAuth2UserInfo oAuthUserInfo = null;
		
		if(registrationId.equals("naver")) {
			oAuthUserInfo = new NaverOAuth2UserInfo(oAuth2User.getAttributes());
		}
		
		// 회원 정보 생성.
		int count = this.providerDao.selectMemberCountByEmail(oAuthUserInfo.getEmail());
		// 존재하지 않는 이메일이었다!
		if(count == 0) {
			// 회원 가입 진행
			RequestRegistMemberVO registMemberVO = new RequestRegistMemberVO();
			registMemberVO.setEmail(oAuthUserInfo.getEmail());
			registMemberVO.setName(oAuthUserInfo.getName());
			registMemberVO.setSalt("-");
			registMemberVO.setPassword("-"); // OAuth 는 salt와 패스워드 모른다
			this.providerDao.insertOAuthMember(registMemberVO);			
			// 		Provider 등록
			OAuthProviderVO oAuthProviderVO = new OAuthProviderVO();
			oAuthProviderVO.setEmail(oAuthProviderVO.getEmail());
			oAuthProviderVO.setProvider(registrationId);
			this.providerDao.insertOAuthProvider(oAuthProviderVO);
			
		}
		// 존재하는 이메일이었다
		else {
			// Provider가 존재하는가? (naver)	
			OAuthProviderVO oAuthProviderVO = new OAuthProviderVO();
			oAuthProviderVO.setEmail(oAuthUserInfo.getEmail());
			oAuthProviderVO.setProvider(registrationId);
			int providerCount = this.providerDao.selectProviderCountByEmailAndProvider(oAuthProviderVO);
			// 없는 provider 였다!
			if(providerCount == 0) {
				// provider만 등록
				this.providerDao.insertOAuthProvider(oAuthProviderVO);
			}
		}
		
		// key 확장 : 무슨 값이 있는지 모르기 때문에 아래처럼 사용
		String nickname = oAuthUserInfo.get("nickname");
		String gender = oAuthUserInfo.get("gender");
		// TODO DB Insert
		// TODO 인증 정보에 추가
		// TODO Setting
		
		// 존재하는 provider 였다!
		// 회원 정보를 조회해서 인증 정보 생성
		MemberVO memberVO = this.memberDao.selectMemberByEmail(oAuthUserInfo.getEmail());
		List<String> roles = this.memberDao.selectRolesByEmail(oAuthUserInfo.getEmail());
		memberVO.setRoles(roles);
		return new SecurityOAuthUser(memberVO, oAuthUserInfo);
	}

	
}
