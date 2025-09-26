package com.ktdsuniversity.edu.member.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.service.MemberService;
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
		
		int insertCount = this.memberDao.insertNewMember(requestRegistMemberVO);
		return insertCount > 0;
	}

	@Override
	public int readMemberCountByEmail(String email) {
		return this.memberDao.selectMemberCountByEmail(email);
	}

}







