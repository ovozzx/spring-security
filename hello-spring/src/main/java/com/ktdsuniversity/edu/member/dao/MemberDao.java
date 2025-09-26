package com.ktdsuniversity.edu.member.dao;

import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

public interface MemberDao {

	int insertNewMember(RequestRegistMemberVO requestRegistMemberVO);

	int selectMemberCountByEmail(String email);

}