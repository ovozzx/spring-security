package com.ktdsuniversity.edu.member.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktdsuniversity.edu.member.dao.MemberDao;
import com.ktdsuniversity.edu.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

}