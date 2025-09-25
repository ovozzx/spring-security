package com.ktdsuniversity.edu.file.service;

import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.file.vo.RequestDownloadVO;

public interface FileService {

	FileVO readFileVO(RequestDownloadVO requestDownloadVO);

}