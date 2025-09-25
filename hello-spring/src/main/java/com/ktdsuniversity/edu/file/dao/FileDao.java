package com.ktdsuniversity.edu.file.dao;

import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.file.vo.RequestDownloadVO;

public interface FileDao {

	int insertFile(FileVO uploadResult);

	int updateDownloadCount(RequestDownloadVO requestDownloadVO);

	FileVO selectFileVO(RequestDownloadVO requestDownloadVO);

}