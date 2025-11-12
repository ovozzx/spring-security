package com.ktdsuniversity.edu.member.dao;

import java.util.List;

import com.ktdsuniversity.edu.member.vo.MemberVO;
import com.ktdsuniversity.edu.member.vo.RequestRegistMemberVO;

public interface MemberDao {

	int insertNewMember(RequestRegistMemberVO requestRegistMemberVO);

	int selectMemberCountByEmail(String email);

	MemberVO selectMemberByEmail(String email);

	int updateLoginFailCountByEmail(String email);

	int updateBlockByEmail(String email);

	int updateLoginSuccessByEmail(String email);

	int selectUnblockMemberByEmail(String email);

	int updateDelYnByEmail(String email);

	List<String> selectRolesByEmail(String username);

}