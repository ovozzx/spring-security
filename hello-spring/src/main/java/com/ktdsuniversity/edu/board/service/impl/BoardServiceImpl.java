package com.ktdsuniversity.edu.board.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestModifyBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestSearchBoardVO;
import com.ktdsuniversity.edu.board.vo.ResponseBoardListVO;
import com.ktdsuniversity.edu.common.exceptions.AjaxException;
import com.ktdsuniversity.edu.common.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.dao.FileGroupDao;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.vo.FileGroupVO;
import com.ktdsuniversity.edu.file.vo.FileVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private MultipartFileHandler multipartFileHandler;
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private FileGroupDao fileGroupDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Override
	public ResponseBoardListVO readBoardList(RequestSearchBoardVO requestSearchBoardVO) {
		
		if (requestSearchBoardVO.getFrom() == null) {
			requestSearchBoardVO.setFrom(LocalDate.now().minusMonths(1).toString());
		}
		if (requestSearchBoardVO.getTo() == null) {
			requestSearchBoardVO.setTo(LocalDate.now().toString());
		}
		
		LocalDate fromDate = LocalDate.parse(requestSearchBoardVO.getFrom());
		LocalDate toDate = LocalDate.parse(requestSearchBoardVO.getTo());
		
		if (fromDate.isAfter(toDate)) {
			fromDate = toDate;
		}
		requestSearchBoardVO.setFrom(fromDate.toString());
		requestSearchBoardVO.setTo(toDate.toString());
		
		
		// 게시글의 개수 필요.
		int count = this.boardDao.selectBoardCount(requestSearchBoardVO);
		requestSearchBoardVO.setPageCount(count);
		
		// 게시글의 목록 필요.
		List<BoardVO> list = this.boardDao.selectBoardList(requestSearchBoardVO);
		
		// 게시글의 개수 + 게시글의 목록 반환.
		ResponseBoardListVO result = new ResponseBoardListVO();
		result.setCount(count);
		result.setList(list);
		
		return result;
		
	}

	@Transactional
	@Override
	public boolean createNewBoard(RequestCreateBoardVO requestCreateBoardVO) {
		
		List<FileVO> uploadResult = this.multipartFileHandler
								  .upload(requestCreateBoardVO.getFile());
		
		if (uploadResult != null && uploadResult.size() > 0) {
			// 1. File Group Insert
			FileGroupVO fileGroupVO = new FileGroupVO();
			fileGroupVO.setFileCount(uploadResult.size());
			int insertGroupCount = this.fileGroupDao.insertFileGroup(fileGroupVO);
			
			// 2. File Insert
			for (FileVO result : uploadResult) {
				result.setFileGroupId(fileGroupVO.getFileGroupId());
				int insertFileCount = this.fileDao.insertFile(result);
			}

			requestCreateBoardVO.setFileGroupId(fileGroupVO.getFileGroupId());
		}
		
		// boardDao를 통해서 insert 를 수행시킨다.
		// 그 결과를 반환시킨다.
		return this.boardDao.insertNewBoard(requestCreateBoardVO) > 0;
	}

	@Transactional
	@Override
	public BoardVO readBoardOneById(String id, boolean doIncreaseViewCount) {
		// 1. 게시글의 조회수를 1 증가시킨다.
		/*
		 * UPDATE BOARD
		 *    SET VIEW_CNT = VIEW_CNT + 1
		 *  WHERE ID = ?
		 */
		if (doIncreaseViewCount) {
			int updateCount = this.boardDao.updateViewCntById(id);
//			Integer.parseInt("sfasdf"); // NumberFormatException
			
			if (updateCount == 0) {
				throw new AjaxException(id + " 게시글은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
			}
		}
		
		// 2. 게시글의 내용을 조회한다.
		BoardVO board = this.boardDao.selectBoardById(id);
		if (board == null) {
			throw new AjaxException(id + " 게시글은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}
		
		// 3. 게시글의 내용을 반환시킨다.
		return board;
	}

	@Transactional
	@Override
	public boolean updateBoardModifyById(RequestModifyBoardVO requestModifyBoardVO) {
		
		BoardVO board = this.boardDao.selectBoardById(requestModifyBoardVO.getId());
		
		// Spring --> Controller가 아닌 영역에서 Session을 가져오기 위한 방법 제공.
//		String email = AuthenticationUtil.getEmail();
//		if ( ! board.getEmail().equals( email )) { // 이미 한 작업 
//			throw new HelloSpringException("잘못된 접근입니다.", "error/403");
//		}
		
		int updateCount = this.boardDao.updateBoardModifyById(requestModifyBoardVO);
		
		if (updateCount == 0) {
			throw new AjaxException(requestModifyBoardVO.getId()  + " 게시글은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}
		
		return updateCount > 0;
	}

	@Transactional
	@Override
	public boolean deleteBoardById(String id) {
		
//		BoardVO board = this.boardDao.selectBoardById(id);
//		String email = AuthenticationUtil.getEmail();
//		if ( ! board.getEmail().equals( email )) {
//			throw new HelloSpringException("잘못된 접근입니다.", "error/403");
//		}
		
		int deleteCount = this.boardDao.deleteBoardById(id);
		if (deleteCount == 0) {
			//throw new HelloSpringException(id + " 게시글은 존재하지 않습니다.", "error/404");
			throw new AjaxException(id + " 게시글은 존재하지 않습니다.", HttpStatus.NOT_FOUND);
		}
		
		return deleteCount > 0;
	}

	@Override
	public List<BoardVO> readAllBoardListForExcel() {
		return this.boardDao.selectBoardListForExcel();
	}

}
