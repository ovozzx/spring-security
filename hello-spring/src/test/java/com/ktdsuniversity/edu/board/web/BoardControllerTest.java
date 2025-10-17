package com.ktdsuniversity.edu.board.web;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.service.impl.BoardServiceImpl;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.member.vo.MemberVO;

@WebMvcTest(BoardController.class)
@Import({MultipartFileHandler.class, BoardServiceImpl.class})
public class BoardControllerTest {

	// BoardController의 End-point를 실행할 가짜 브라우저
	@Autowired
	private MockMvc mvc;
	
	@MockitoBean
	private MultipartFileHandler fileHandler;
	
	@MockitoBean
	private BoardService boardService;
	
	@Test
	@DisplayName("글쓰기 페이지 보여주기")
	public void viewBoardWritePageTest() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("__LOGIN_USER__", new MemberVO());
		
		this.mvc.perform( get("/write").session(session) )
				.andDo( print() )
				.andExpect(status().isOk())
				.andExpect(view().name("board/write"));
	}
	
	@Test
	@DisplayName("글쓰기 페이지 보여주기 - 세션없음 - 로그인페이지로 이동")
	public void viewBoardWritePageTestRedirectLoginPage() throws Exception {
		
		this.mvc.perform( get("/write") )
				.andDo( print() )
				.andExpect(status().isOk())
				.andExpect( forwardedUrl("/WEB-INF/views/member/login.jsp") );
	}
	
	@Test
	@DisplayName("글 작성 하기 - 첨부파일 전송")
	public void doWriteBoardActionTestWithFile() throws Exception {
		
		// given
		BDDMockito.given(this.boardService.createNewBoard(any(RequestCreateBoardVO.class)))
				  .will((obj) -> {
					  // this.boardService.createNewBoard 로 전달된 파라미터 받아오기
					  RequestCreateBoardVO vo = (RequestCreateBoardVO) obj.getArgument(0);
					  vo.setId("test-id");
					  return true;
				  });
//				  .willReturn(true);
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("__LOGIN_USER__", new MemberVO());
		
		MockMultipartFile mockFile = 
				new MockMultipartFile(
						"file",  // 파라미터 명
						"testfile.png",  // 파일 명
						"image/png",  // 파일 타입
						new byte[10] // 파일 내용
				);
		
		/**
		 * Java Servlet
		 * 	-> Request 의 두 가지 종류
		 * 		-> 일반 Request : HttpServletRequest
		 * 		-> MultipartRequest : MultipartHttpServletRequest
		 */
		
		this.mvc.perform( multipart("/write")
							.file(mockFile)
							.param("subject", "테스트")
							.param("content", "테스트")
							.session(session) )
				.andDo(print())
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/view/test-id"));
	}
	
}














