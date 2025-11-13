package com.ktdsuniversity.edu.board.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardExcelVO;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.common.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.util.ResourceUtil;

import io.github.seccoding.excel.write.Write;

@PreAuthorize("isAuthenticated()") // 동일한 규칙이면 이렇게 가능. 하나하나 달 필요 없음
@Controller
@RequestMapping("/api/v1/boards")
public class BoardDownloadController { // rest가 아닌 컨트롤러
	
	@Autowired
	private MultipartFileHandler multipartFileHandler;
	
	@Autowired
	private BoardService boardService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	@GetMapping("/download-article") // 보통 관리자 기능
	public ResponseEntity<Resource> downloadArticle() { 
		
		// 데이터베이스에 등록된 모든 게시글을 조회.
		// Pagination이 적용되지 않을 새로운 서비스를 통해서 조회.
		List<BoardVO> boardList = this.boardService.readAllBoardListForExcel();
		
		// Excel 파일 생성 및 컨텐츠 작성.
		Workbook workbook = new SXSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("게시글 목록");
		
		Row row = sheet.createRow(0);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("번호");
		
		cell = row.createCell(1);
		cell.setCellValue("제목");
		
		cell = row.createCell(2);
		cell.setCellValue("이름");
		
		cell = row.createCell(3);
		cell.setCellValue("첨부파일");
		
		cell = row.createCell(4);
		cell.setCellValue("조회수");
		
		cell = row.createCell(5);
		cell.setCellValue("등록일");
		
		cell = row.createCell(6);
		cell.setCellValue("수정일");
		
		int rowIndex = 1;
		for (BoardVO board: boardList ) {
			
			row = sheet.createRow(rowIndex);
			
			cell = row.createCell(0);
			cell.setCellValue(board.getNumber());
			
			cell = row.createCell(1);
			cell.setCellValue(board.getSubject());
			
			cell = row.createCell(2);
			cell.setCellValue(board.getMemberVO().getName());
			
			cell = row.createCell(3);
			if (board.getFileGroupVO() != null) {
				cell.setCellValue(board.getFileGroupVO()
									   .getFile()
									   .stream()
									   .map(file -> file.getFileDisplayName())
									   .collect(Collectors.joining(", ")));
			}
			
			cell = row.createCell(4);
			cell.setCellValue(board.getViewCnt());
			
			cell = row.createCell(5);
			cell.setCellValue(board.getCrtDt());
			
			cell = row.createCell(6);
			cell.setCellValue(board.getMdfyDt());
			
			rowIndex++;
		}
		
		File excelFile = this.multipartFileHandler.makeTemporaryFile();
		OutputStream fileOutputStream = null;
		
		try {
			fileOutputStream = new FileOutputStream(excelFile);
			workbook.write(fileOutputStream);
		} catch (IOException e) {
			throw new HelloSpringException("엑셀파일을 만들수 없습니다.", "error/500");
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {}
			
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {}
			}
		}
		
		LocalDate now = LocalDate.now();
		String filename = "게시글목록_" + now + ".xlsx";
		
		return ResourceUtil.getResource(excelFile, filename, "application/octet-stream");
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	@GetMapping("/download-article-2")
	public ResponseEntity<Resource> downloadArticle2() {
		List<BoardVO> boardList = this.boardService.readAllBoardListForExcel();
		
		List<BoardExcelVO> excelDataList = boardList.stream()
													.map(BoardExcelVO::new)
													.toList();
		
		Write<BoardExcelVO> write = new Write<>(BoardExcelVO.class, excelDataList);
		write.write(UUID.randomUUID().toString() + ".xlsx");
		
		File excelFile = this.multipartFileHandler.makeTemporaryFile();
		write.toFile(excelFile);
		
		LocalDate now = LocalDate.now();
		String filename = "게시글목록_" + now + ".xlsx";
		
		return ResourceUtil.getResource(excelFile, filename, "application/octet-stream");
	}
	

}
