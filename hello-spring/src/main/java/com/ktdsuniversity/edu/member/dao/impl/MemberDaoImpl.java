package com.ktdsuniversity.edu.member.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.member.dao.MemberDao;

@Repository
public class MemberDaoImpl extends SqlSessionDaoSupport implements MemberDao {

    private final String NAME_SPACE = "com.ktdsuniversity.edu.member.dao.impl.MemberDaoImpl.";

    @Autowired
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }


}