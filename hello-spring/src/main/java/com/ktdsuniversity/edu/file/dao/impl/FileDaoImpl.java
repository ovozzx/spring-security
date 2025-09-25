package com.ktdsuniversity.edu.file.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.file.vo.RequestDownloadVO;

@Repository
public class FileDaoImpl extends SqlSessionDaoSupport implements FileDao {

    private final String NAME_SPACE = "com.ktdsuniversity.edu.file.dao.impl.FileDaoImpl.";

    @Autowired
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

	@Override
	public int insertFile(FileVO uploadResult) {
		return super.getSqlSession().insert(this.NAME_SPACE + "insertFile", uploadResult);
	}

	@Override
	public int updateDownloadCount(RequestDownloadVO requestDownloadVO) {
		return super.getSqlSession().update(this.NAME_SPACE + "updateDownloadCount", requestDownloadVO);
	}

	@Override
	public FileVO selectFileVO(RequestDownloadVO requestDownloadVO) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectFileVO", requestDownloadVO);
	}


}