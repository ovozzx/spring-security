package com.ktdsuniversity.edu.member.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.dao.impl.MemberDaoImpl;
import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
@Import({MemberServiceImpl.class, MemberDaoImpl.class})
public class MemberServiceImplTest {
	
	@Autowired
	private MemberService memberService;
	
	// @MockBean 대신 @MockitoBean으로 사용.
	@MockitoBean
	private MemberDao memberDao;
	
	@Test
	@DisplayName("이메일 중복검사 실패 테스트 - 이미 등록된 이메일로 검사 시도함.")
	public void readMemberCountByEmailFailTest() {
		// memberServiceImpl.readMemberCountByEmail()
		// 가 memberDaoImpl.selectMemberCountByEmail() 동작.
		// 그 결과를 반환(boolean)
		
		// given - when - then test
		// given: 의존하고 있는 클래스의 메소드가 어떻게 동작해야할지 명시해주는 것.
		BDDMockito.given(this.memberDao.selectMemberCountByEmail("a@a.com"))
				  .willReturn(1);
		
		// when: 테스트 하려는 클래스의 메소드를 동작시키는 것.
		int emailCount = this.memberService.readMemberCountByEmail("a@a.com");
		
		// then: 기대하는 테스트의 결과를 검증.
		assertTrue(emailCount == 1);
		
		// verify: given이 정확하게 동작되었는지 검증.
		Mockito.verify(this.memberDao).selectMemberCountByEmail("a@a.com");
	}
	
	
	@Test
	@DisplayName("이메일 중복검사 성공 테스트")
	public void readMemberCountByEmailSuccessTest() {
		// memberServiceImpl.readMemberCountByEmail()
		// 가 memberDaoImpl.selectMemberCountByEmail() 동작.
		// 그 결과를 반환(boolean)
		
		// given - when - then test
		// given: 의존하고 있는 클래스의 메소드가 어떻게 동작해야할지 명시해주는 것.
		BDDMockito.given(this.memberDao.selectMemberCountByEmail("a@a.com"))
				  .willReturn(0);
		
		// when: 테스트 하려는 클래스의 메소드를 동작시키는 것.
		int emailCount = this.memberService.readMemberCountByEmail("a@a.com");
		
		// then: 기대하는 테스트의 결과를 검증.
		assertTrue(emailCount == 0);
		
		// verify: given이 정확하게 동작되었는지 검증.
		Mockito.verify(this.memberDao).selectMemberCountByEmail("a@a.com");
	}
	
	@Test
	@DisplayName("회원가입 실패 테스트 - 파라미터 null")
	public void createNewMemberFailTestNull() {
		// given
//		BDDMockito.given(this.memberDao.selectMemberCountByEmail(null))
//			      .willReturn(0);
//		BDDMockito.given(this.memberDao.insertNewMember(null))
//				  .willReturn(0);
		
		// when
		boolean createResult = this.memberService.createNewMember(null);
		
		// then
		assertFalse(createResult);
		
		// verify
//		Mockito.verify(this.memberDao).selectMemberCountByEmail(null);
//		Mockito.verify(this.memberDao).insertNewMember(null);
	}
	
	@Test
	@DisplayName("회원가입 실패 테스트 - 파라미터는 null 아님, 멤버변수가 null")
	public void createNewMemberFailTestMemberVariableNull() {
		
		RequestRegistMemberVO memberVO = new RequestRegistMemberVO();
		
		// given
		BDDMockito.given(this.memberDao.selectMemberCountByEmail(null))
			      .willReturn(0);
//		BDDMockito.given(this.memberDao.insertNewMember(memberVO))
//				  .willReturn(0);
		
		// when
		boolean createResult = this.memberService.createNewMember(memberVO);
		
		// then
		assertFalse(createResult);
		
		// verify
		Mockito.verify(this.memberDao).selectMemberCountByEmail(null);
//		Mockito.verify(this.memberDao).insertNewMember(memberVO);
	}
	
	@Test
	@DisplayName("회원가입 실패 테스트 - Insert 실패")
	public void createNewMemberFailTestInsertFail() {
		
		RequestRegistMemberVO memberVO = new RequestRegistMemberVO();
		memberVO.setEmail("a@a.com");
		memberVO.setPassword("password");
		memberVO.setPasswordConfirm("password");
		memberVO.setName("user");
		
		// given
		BDDMockito.given(this.memberDao.selectMemberCountByEmail("a@a.com"))
			      .willReturn(0);
		BDDMockito.given(this.memberDao.insertNewMember(memberVO))
				  .willReturn(0);
		
		// when
		boolean createResult = this.memberService.createNewMember(memberVO);
		
		// then
		assertFalse(createResult);
		
		// verify
		Mockito.verify(this.memberDao).selectMemberCountByEmail("a@a.com");
		Mockito.verify(this.memberDao).insertNewMember(memberVO);
	}
	
	@Test
	@DisplayName("회원가입 성공 테스트")
	public void createNewMemberSuccessTest() {
		
		RequestRegistMemberVO memberVO = new RequestRegistMemberVO();
		memberVO.setEmail("a@a.com");
		memberVO.setPassword("password");
		memberVO.setPasswordConfirm("password");
		memberVO.setName("user");
		
		// given
		BDDMockito.given(this.memberDao.selectMemberCountByEmail("a@a.com"))
			      .willReturn(0);
		BDDMockito.given(this.memberDao.insertNewMember(memberVO))
				  .willReturn(1);
		
		// when
		boolean createResult = this.memberService.createNewMember(memberVO);
		
		// then
		assertTrue(createResult);
		
		// verify
		Mockito.verify(this.memberDao).selectMemberCountByEmail("a@a.com");
		Mockito.verify(this.memberDao).insertNewMember(memberVO);
	}
}




