package com.ktdsuniversity.edu.board.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Delete;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardExcelVO;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestModifyBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestSearchBoardVO;
import com.ktdsuniversity.edu.board.vo.ResponseBoardListVO;
import com.ktdsuniversity.edu.common.exceptions.AjaxException;
import com.ktdsuniversity.edu.common.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.common.util.AuthenticationUtil;
import com.ktdsuniversity.edu.common.vo.AjaxResponse;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.util.ResourceUtil;

import io.github.seccoding.excel.write.Write;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/boards") // 방식을 가리지 않는 end point (get, post, ... 뭐든) => suffix로 모두 붙여주겠다
public class BoardApi {
private static final Logger logger = LoggerFactory.getLogger(BoardApi.class);
	
	@Autowired
	private MultipartFileHandler multipartFileHandler;
	
	@Autowired
	private BoardService boardService;

	// http://localhost:8080/list?pageNo=0&listSize=20
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/") // 패치하면 v 버전을 바꿈 => /api/v1/boards
	public AjaxResponse getBoardListPage(
			RequestSearchBoardVO requestSearchBoardVO) { // @RequestBody는 POST & PUT에만 가능
		
		ResponseBoardListVO result = this.boardService.readBoardList(requestSearchBoardVO);

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setBody(result);
		return ajaxResponse;
	}

	@PreAuthorize("isAuthenticated()") // 인증 검사인데, 이 메소드가 동작되기 이전에 검사 => 이것을 동작하기 위해 만족하는 권한을 가지고 있는 지를 검사한다
	// 인증만 되어있으면 누구나 동작 가능하니까, 인증만 검사하고 권한 검사는 안 함
	//@PostAuthorize // 인증 검사, 이 메소드가 동작된 이후에 검사 => 반환시킨 데이터가 내가 접근 가능한 지를 검사한다
	@GetMapping("/write")
	public String viewBoardWritePage() {
		return "board/write";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/") // => /api/v1/boards
	public AjaxResponse createBoard(@Valid RequestCreateBoardVO requestCreateBoardVO, BindingResult bindingResult,
			Authentication authentication) {
		// multipartFile이 있으면 RequestBody를 붙이면 안 됨

	    logger.debug(requestCreateBoardVO.toString());
		
		if (bindingResult.hasErrors()) {
			throw new AjaxException(null, HttpStatus.BAD_REQUEST, bindingResult.getFieldErrors());
		}

		requestCreateBoardVO.setEmail(AuthenticationUtil.getEmail());

		// 게시글 등록 트랜잭션 요청.
		boolean createResult = this.boardService.createNewBoard(requestCreateBoardVO);

		// 게시글 등록이 성공했다면, 게시글의 상세페이지로 이동한다.
		// 이동을 처리하는 View ==> RedirectView
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setBody(requestCreateBoardVO.getId());
		return ajaxResponse;
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

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}") // => /api/v1/boards/{id}
	public AjaxResponse getBoardById(@PathVariable String id) {

		// 1. 게시글을 조회해온다. from boardService.
		BoardVO board = this.boardService.readBoardOneById(id, true);
		
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setBody(board);

		return ajaxResponse;
	}

	/*
	 * GET 방식으로 /modify/{id} 로 접근할 경우 id에 해당되는 게시글의 정보를 모두 읽어서 게시글 수정 페이지에 노출시킨다.
	 * 게시글 수정 페이지: "board/modify"
	 * 
	 * 게시글의 정보를 읽는다 ==> boardService.readBoardOneById(id); 사용.
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or @boardAuthHelper.isResourceOwner(#id)") 
	// 관리자인지 확인, 권한 확인하면 인증도 이미 확인된 거 or 내가 쓴 글인지
    // @ 붙이면 Bean에 있는 거 부를 수 있음 -> 앞글자만 소문자로 바뀌어서 들어감. 
	// #id하면 id 받아서 넣을 수 있음
	@GetMapping("/modify/{id}") // 관리자 or 작성한 사람 가능
	public String viewBoardModifyPage(
			@PathVariable String id, Model model,
			Authentication authentication
		) {
		
		BoardVO board = this.boardService.readBoardOneById(id, false);
		
//		if ( ! board.getEmail().equals( AuthenticationUtil.getEmail() )) { 이제 security에서 미리 해주기때문에 필요 없음
//			throw new HelloSpringException("잘못된 접근입니다.", "error/403");
//		}
		
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or @boardAuthHelper.isResourceOwner(#id)") 
	@PutMapping("/{id}") // => /api/v1/boards/{id} api에서 post는 put을 씀
	public AjaxResponse modifyBoard(@PathVariable String id, 
			@RequestBody @Valid RequestModifyBoardVO requestModifyBoardVO, // multipart 없어서 json으로 가능
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new AjaxException(null, HttpStatus.BAD_REQUEST, bindingResult.getFieldErrors());
		}

		requestModifyBoardVO.setId(id);

		boolean updateResult = this.boardService.updateBoardModifyById(requestModifyBoardVO);

		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setBody(updateResult);
		// 수정이 완료되면 "/list" URL로 이동시킨다.
		return ajaxResponse;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or @boardAuthHelper.isResourceOwner(#id)") 
	@DeleteMapping("/{id}") // 데이터 변화 => api 대상 . PUT이랑 뭐만 데이터 보낼 수 있음
	public AjaxResponse deleteBoard(@PathVariable String id) {
		boolean deleteResult = this.boardService.deleteBoardById(id);
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setBody(deleteResult);
		return ajaxResponse;
	}
	
	// 첨부파일은 API로 못함
	
	

}
