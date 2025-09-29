package com.ktdsuniversity.edu.member.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktdsuniversity.edu.common.util.SHAEncrypter;
import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.service.MemberService;
import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.member.vo.RequestMemberLoginVO;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

	@Override
	public boolean createNewMember(RequestRegistMemberVO requestRegistMemberVO) {
		int emailCount = this.memberDao.selectMemberCountByEmail( requestRegistMemberVO.getEmail() );
		if (emailCount == 1) {
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
		}
		
		// 비밀번호 암호화.
		// SALT 발급.
		String salt = SHAEncrypter.generateSalt();
		// SALT를 이용해 비밀번호 암호화.
		String encryptedPassword = SHAEncrypter.getEncrypt(requestRegistMemberVO.getPassword(), salt);
		
		requestRegistMemberVO.setPassword(encryptedPassword);
		requestRegistMemberVO.setSalt(salt);
		
		int insertCount = this.memberDao.insertNewMember(requestRegistMemberVO);
		return insertCount > 0;
	}

	@Override
	public int readMemberCountByEmail(String email) {
		return this.memberDao.selectMemberCountByEmail(email);
	}

	@Override
	public MemberVO readMember(RequestMemberLoginVO requestMemberLoginVO) {
		// 1. Email을 이용해 회원의 정보를 조회.
		MemberVO memberVO = this.memberDao.selectMemberByEmail( requestMemberLoginVO.getEmail() );
		// 2. 조회된 회원이 없을 경우(null)는 Exception Throw.
		if (memberVO == null) {
			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
		}
		// 3. 조회된 회원이 있을 경우 SALT값만 가져옴.
		String salt = memberVO.getSalt();
		// 4. SALT를 이용해서 사용자가 입력한 비밀번호를 암호화.
		String encryptedPassword = SHAEncrypter.getEncrypt(requestMemberLoginVO.getPassword(), salt);
		// 5. 암호화된 비밀번호와 조회한 회원의 비밀번호가 일치하는지 검사.
		if ( ! memberVO.getPassword().equals(encryptedPassword) ) {
			// 6. 일치하지 않을 경우는 Exception Throw.
			throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
		}
		// 7. 일치할 경우 조회한 회원의 정보를 반환.
		return memberVO;
	}

}







