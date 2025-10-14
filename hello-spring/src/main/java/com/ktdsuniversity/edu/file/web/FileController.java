package com.ktdsuniversity.edu.file.web;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.file.service.FileService;
import com.ktdsuniversity.edu.file.util.ResourceUtil;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.file.vo.RequestDownloadVO;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;
    
    @GetMapping("/file/{id}/{fileGroupId}/{fileId}")
    public ResponseEntity<Resource> doDownloadAction(
    		@PathVariable String id,
    		@PathVariable String fileGroupId,
    		@PathVariable String fileId,
    		@SessionAttribute(value="__LOGIN_USER__", required=false) MemberVO loginUser) {
    	
    	if (loginUser == null) {
    		return null;
    	}
    	
    	RequestDownloadVO requestDownloadVO = new RequestDownloadVO();
    	requestDownloadVO.setId(id);
    	requestDownloadVO.setFileGroupId(fileGroupId);
    	requestDownloadVO.setFileId(fileId);
    	
    	FileVO downloadFile = this.fileService.readFileVO(requestDownloadVO);
    	
    	String filename = downloadFile.getFileDisplayName();
    	String filePath = downloadFile.getFilePath();
    	String mimeType = downloadFile.getFileType();
    	
    	// Download 시작.
    	File file = new File(filePath);
    	
    	return ResourceUtil.getResource(file, filename, mimeType);
    }
}





