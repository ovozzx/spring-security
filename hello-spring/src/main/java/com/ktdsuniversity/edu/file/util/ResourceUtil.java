package com.ktdsuniversity.edu.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class ResourceUtil {

	public static ResponseEntity<Resource> getResource(
			File file, String filename, String mimeType) {
		
		try {
			filename = URLEncoder.encode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {}
		
		// Http Response Headers
    	// File을 다운로드 한다는 정보를 작성.
    	HttpHeaders header = new HttpHeaders();
    	// 다운로드할 파일의 이름은 무엇?
    	header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
    	// 다운로드할 파일의 타입은 무엇?
    	header.add(HttpHeaders.CONTENT_TYPE, mimeType);
    	// 다운로드할 파일의 크기는 얼마?
    	header.add(HttpHeaders.CONTENT_LENGTH, file.length() + "");
    	
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
