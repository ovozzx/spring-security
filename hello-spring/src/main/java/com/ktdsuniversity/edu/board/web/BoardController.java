package com.ktdsuniversity.edu.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestModifyBoardVO;
import com.ktdsuniversity.edu.board.vo.ResponseBoardListVO;
import com.ktdsuniversity.edu.member.vo.MemberVO;

import jakarta.validation.Valid;

@Controller
public class BoardController {
	
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
	public String doWriteBoardAction(
			@Valid RequestCreateBoardVO requestCreateBoardVO,
			BindingResult bindingResult,
			@SessionAttribute("__LOGIN_USER__") MemberVO loginUser,
			Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("writeData", requestCreateBoardVO);
			return "board/write";
		}
		
		requestCreateBoardVO.setEmail( loginUser.getEmail() );
		
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
	 * GET 방식으로 /modify/{id} 로 접근할 경우
	 * id에 해당되는 게시글의 정보를 모두 읽어서 게시글 수정 페이지에 노출시킨다.
	 * 게시글 수정 페이지: "board/modify"
	 * 
	 * 게시글의 정보를 읽는다 ==> boardService.readBoardOneById(id); 사용.
	 */
	@GetMapping("/modify/{id}")
	public String viewBoardModifyPage(@PathVariable String id, Model model) {
		BoardVO board = this.boardService.readBoardOneById(id, false);
		model.addAttribute("board", board);
		return "board/modify";
	}
	
	/*
	 * POST 방식으로 /modify/{id} 로 요청을 받으면
	 * 게시글 수정 정보 (RequestModifyBoardVO)를 파라미터로 받아와
	 * update query를 동작시킨다.
	 * 
	 * controller - doBoardModifyAction
	 * service - updateBoardModifyById(RequestModifyBoardVO)
	 * dao - updateBoardModifyById(RequestModifyBoardVO)
	 */
	@PostMapping("/modify/{id}")
	public String doBoardModifyAction(@PathVariable String id, 
									  @Valid RequestModifyBoardVO requestModifyBoardVO,
									  BindingResult bindingResult,
									  Model model) {
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
	
}






