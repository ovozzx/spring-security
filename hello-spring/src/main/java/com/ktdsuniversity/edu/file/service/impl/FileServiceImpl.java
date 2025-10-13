package com.ktdsuniversity.edu.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.common.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.service.FileService;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.file.vo.RequestDownloadVO;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

    @Transactional
	@Override
	public FileVO readFileVO(RequestDownloadVO requestDownloadVO) {
		// 파일 다운로드 횟수 증가.
		int updateCount = this.fileDao.updateDownloadCount(requestDownloadVO);
		if (updateCount == 0) {
			throw new HelloSpringException("존재하지 않는 파일입니다.", "error/404");
		}
		
		// 파일 정보 조회 후 반환.
		return this.fileDao.selectFileVO(requestDownloadVO);
	}

}