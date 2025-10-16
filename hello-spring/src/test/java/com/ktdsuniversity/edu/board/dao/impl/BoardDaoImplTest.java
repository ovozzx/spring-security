package com.ktdsuniversity.edu.board.dao.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.UncategorizedSQLException;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BoardDaoImpl.class)
public class BoardDaoImplTest {

	@Autowired
	private BoardDao boardDao;
	
	@Test
	@DisplayName("게시글 삭제 실패 테스트")
	public void deleteBoardByIdFailTest() {
		int deleteCount = this.boardDao.deleteBoardById("asldkfjlsdkajflafsd");
		assertTrue(deleteCount == 0);
	}
	
	@Test
	@DisplayName("게시글 삭제 실패 테스트 - 파라미터 null")
	public void deleteBoardByIdFailTestNull() {
		int deleteCount = this.boardDao.deleteBoardById(null);
		assertTrue(deleteCount == 0);
	}
	
	@Test
	@DisplayName("게시글 삭제 실패 테스트 - 파라미터 SQL Injection")
	public void deleteBoardByIdFailTestSqlInjection() {
		int deleteCount = this.boardDao.deleteBoardById("' OR 1 = 1 --");
		assertTrue(deleteCount == 0);
	}
	
	@Test
	@DisplayName("게시글 삭제 성공 테스트")
	public void deleteBoardByIdSuccessTest() {
		// junit insert, update, delete ==> rollback
		int deleteCount = this.boardDao.deleteBoardById("AR-20250925-000044");
		assertTrue(deleteCount == 1);
	}
	
	@Test
	@DisplayName("게시글 작성 실패 테스트 - 파라미터 null")
	public void insertNewBoardFailTestNull() {
		int insertCount = this.boardDao.insertNewBoard(null);
		assertTrue(insertCount == 0);
	}
	
	@Test
	@DisplayName("게시글 작성 실패 테스트 - 파라미터 값이 매우 김")
	public void insertNewBoardFailTestTooLongValue() {
		RequestCreateBoardVO create = new RequestCreateBoardVO();
		create.setSubject("Test");
		create.setContent("Test");
		create.setEmail("Test");
		create.setFileGroupId("123456789012345678901234");
		
		UncategorizedSQLException sqle = assertThrows(
								UncategorizedSQLException.class, 
							    () -> this.boardDao.insertNewBoard(create));
		
		String exceptionMessage = sqle.getMessage();
		assertTrue(exceptionMessage.contains("ORA-12899"));
	}
	
	@Test
	@DisplayName("게시글 작성 성공 테스트")
	public void insertNewBoardSuccessTest() {
		RequestCreateBoardVO create = new RequestCreateBoardVO();
		create.setSubject("Test");
		create.setContent("Test");
		create.setEmail("Test");
		
		int insertCount = this.boardDao.insertNewBoard(create);
		assertTrue(insertCount == 1);
	}
	
}






