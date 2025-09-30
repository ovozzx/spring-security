package com.ktdsuniversity.edu.file.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.file.service.FileService;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.file.vo.RequestDownloadVO;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;
    
    @GetMapping("/file/{boardId}/{fileGroupId}/{fileId}")
    public ResponseEntity<Resource> doDownloadAction(
    		@PathVariable String boardId,
    		@PathVariable String fileGroupId,
    		@PathVariable String fileId,
    		@SessionAttribute(value="__LOGIN_USER__", required=false) MemberVO loginUser) {
    	
    	if (loginUser == null) {
    		return null;
    	}
    	
    	RequestDownloadVO requestDownloadVO = new RequestDownloadVO();
    	requestDownloadVO.setBoardId(boardId);
    	requestDownloadVO.setFileGroupId(fileGroupId);
    	requestDownloadVO.setFileId(fileId);
    	
    	FileVO downloadFile = this.fileService.readFileVO(requestDownloadVO);
    	
    	String filename = downloadFile.getFileDisplayName();
    	long fileSize = downloadFile.getFileSize();
    	String filePath = downloadFile.getFilePath();
    	String mimeType = downloadFile.getFileType();
    	
    	try {
			filename = URLEncoder.encode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {}
    	
    	// Download 시작.
    	File file = new File(filePath);
    	
    	// Http Response Headers
    	// File을 다운로드 한다는 정보를 작성.
    	HttpHeaders header = new HttpHeaders();
    	// 다운로드할 파일의 이름은 무엇?
    	header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
    	// 다운로드할 파일의 타입은 무엇?
    	header.add(HttpHeaders.CONTENT_TYPE, mimeType);
    	// 다운로드할 파일의 크기는 얼마?
    	header.add(HttpHeaders.CONTENT_LENGTH, fileSize + "");
    	
    	// Java에서 브라우저로 파일을 전송.
    	InputStreamResource downloadResource = null;
    	try {
			downloadResource = new InputStreamResource( new FileInputStream(file) );
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("파일이 존재하지 않습니다.");
		}
    	
    	return ResponseEntity.ok()
    						 .headers(header)
    						 .body(downloadResource);
    }
}





