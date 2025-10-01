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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardExcelVO;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestModifyBoardVO;
import com.ktdsuniversity.edu.board.vo.ResponseBoardListVO;
import com.ktdsuniversity.edu.common.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.util.ResourceUtil;
import com.ktdsuniversity.edu.member.vo.MemberVO;

import io.github.seccoding.excel.write.Write;
import jakarta.validation.Valid;

@Controller
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private MultipartFileHandler multipartFileHandler;
	
	@Autowired
	private BoardService boardService;

	@GetMapping("/list")
	public String viewBoardListPage(Model model) {
		ResponseBoardListVO result = this.boardService.readBoardList();
		model.addAttribute("list", result);

		// /WEB-INF/views/board/list.jsp
		return "board/list";
	}

	@GetMapping("/write")
	public String viewBoardWritePage() {
		return "board/write";
	}

	@PostMapping("/write")
	public String doWriteBoardAction(@Valid RequestCreateBoardVO requestCreateBoardVO, BindingResult bindingResult,
			@SessionAttribute(value = "__LOGIN_USER__", required = false) MemberVO loginUser, Model model) {

		logger.debug(requestCreateBoardVO.toString());
		
		if (bindingResult.hasErrors()) {
			logger.debug("Validation Fail! : " + bindingResult);
			model.addAttribute("writeData", requestCreateBoardVO);
			return "board/write";
		}

		requestCreateBoardVO.setEmail(loginUser.getEmail());

		// 게시글 등록 트랜잭션 요청.
		boolean createResult = this.boardService.createNewBoard(requestCreateBoardVO);

		// 게시글 등록이 성공했다면, 게시글의 상세페이지로 이동한다.
		// 이동을 처리하는 View ==> RedirectView
		return "redirect:/view/" + requestCreateBoardVO.getId();
	}

//	@PostMapping("/write")
//	public String doWriteBoardAction( 
//			@RequestParam String subject, 
//			@RequestParam String email, 
//			@RequestParam String content,
//			HttpServletRequest request ) {
//		
//		System.out.println("Subejct: " + subject);
//		System.out.println("Email: " + email);
//		System.out.println("Content: " + content);
//		System.out.println("UserIP: " + request.getRemoteAddr());
//		return "";
//	}

//	@PostMapping("/write")
//	public String doWriteBoardAction(HttpServletRequest request) {
//		String subject = request.getParameter("subject");
//		String email = request.getParameter("email");
//		String content = request.getParameter("content");
//		
//		String userIp = request.getRemoteAddr();
//		
//		System.out.println("Subject: " + subject);
//		System.out.println("Email: " + email);
//		System.out.println("Content: " + content);
//		System.out.println("User IP: " + userIp);
//		
//		// 브라우저가 보내준 Form Data(Payload)를 가져오는 세 가지 방법
//		// 1. 브라우저의 요청정보를 이용해 가져오기 (HttpServletRequest 이용)
//		// 2. Payload의 값들만 하나씩 가져오기 (@RequestParam 이용)
//		// 3. Payload의 값을 통채로 가져오기 (Command Object 이용)
//		
//		return "";
//	}

	@GetMapping("/view/{id}")
	public String viewBoardDetailPage(@PathVariable String id, Model model) {

		// 1. 게시글을 조회해온다. from boardService.
		BoardVO board = this.boardService.readBoardOneById(id, true);

		// 2. View로 보내준다. (게시글의 내용)
		model.addAttribute("board", board);

		return "board/view";
	}

	/*
	 * GET 방식으로 /modify/{id} 로 접근할 경우 id에 해당되는 게시글의 정보를 모두 읽어서 게시글 수정 페이지에 노출시킨다.
	 * 게시글 수정 페이지: "board/modify"
	 * 
	 * 게시글의 정보를 읽는다 ==> boardService.readBoardOneById(id); 사용.
	 */
	@GetMapping("/modify/{id}")
	public String viewBoardModifyPage(
			@PathVariable String id, Model model,
			@SessionAttribute("__LOGIN_USER__") MemberVO loginUser
		) {
		
		BoardVO board = this.boardService.readBoardOneById(id, false);
		
		if ( ! board.getEmail().equals( loginUser.getEmail() )) {
			throw new HelloSpringException("잘못된 접근입니다.", "error/403");
		}
		
		model.addAttribute("board", board);
		return "board/modify";
	}

	/*
	 * POST 방식으로 /modify/{id} 로 요청을 받으면 게시글 수정 정보 (RequestModifyBoardVO)를 파라미터로 받아와
	 * update query를 동작시킨다.
	 * 
	 * controller - doBoardModifyAction service -
	 * updateBoardModifyById(RequestModifyBoardVO) dao -
	 * updateBoardModifyById(RequestModifyBoardVO)
	 */
	@PostMapping("/modify/{id}")
	public String doBoardModifyAction(@PathVariable String id, 
			@Valid RequestModifyBoardVO requestModifyBoardVO,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("board", requestModifyBoardVO);
			return "board/modify";
		}

		requestModifyBoardVO.setId(id);

		boolean updateResult = this.boardService.updateBoardModifyById(requestModifyBoardVO);

		// 수정이 완료되면 "/list" URL로 이동시킨다.
		return "redirect:/list";
	}

	@GetMapping("/delete/{id}")
	public String doDeleteBoardAction(@PathVariable String id) {
		boolean deleteResult = this.boardService.deleteBoardById(id);

		return "redirect:/list";
	}
	
	@GetMapping("/download-article")
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
