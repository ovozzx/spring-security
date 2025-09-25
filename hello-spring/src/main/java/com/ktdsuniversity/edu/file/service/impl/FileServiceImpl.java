package com.ktdsuniversity.edu.file.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.service.FileService;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.file.vo.RequestDownloadVO;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

	@Override
	public FileVO readFileVO(RequestDownloadVO requestDownloadVO) {
		// 파일 다운로드 횟수 증가.
		int updateCount = this.fileDao.updateDownloadCount(requestDownloadVO);
		if (updateCount == 0) {
			throw new IllegalArgumentException("존재하지 않는 파일입니다.");
		}
		
		// 파일 정보 조회 후 반환.
		return this.fileDao.selectFileVO(requestDownloadVO);
	}

}