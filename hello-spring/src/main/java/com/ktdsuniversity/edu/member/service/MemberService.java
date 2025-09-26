package com.ktdsuniversity.edu.member.service;

import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

public interface MemberService {

	boolean createNewMember(RequestRegistMemberVO requestRegistMemberVO);

	int readMemberCountByEmail(String email);

}