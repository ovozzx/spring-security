package com.ktdsuniversity.edu.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.board.vo.RequestModifyBoardVO;
import com.ktdsuniversity.edu.board.vo.ResponseBoardListVO;
import com.ktdsuniversity.edu.common.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.common.util.SessionUtil;
import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.dao.FileGroupDao;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.vo.FileGroupVO;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.member.vo.MemberVO;

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
	public ResponseBoardListVO readBoardList() {
		
		// 게시글의 개수 필요.
		int count = this.boardDao.selectBoardCount();
		
		// 게시글의 목록 필요.
		List<BoardVO> list = this.boardDao.selectBoardList();
		
		// 게시글의 개수 + 게시글의 목록 반환.
		ResponseBoardListVO result = new ResponseBoardListVO();
		result.setCount(count);
		result.setList(list);
		
		return result;
		
	}

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
			if (updateCount == 0) {
				throw new HelloSpringException(id + " 게시글은 존재하지 않습니다.", "error/404");
			}
		}
		
		// 2. 게시글의 내용을 조회한다.
		BoardVO board = this.boardDao.selectBoardById(id);
		if (board == null) {
			throw new HelloSpringException(id + " 게시글은 존재하지 않습니다.", "error/404");
		}
		
		// 3. 게시글의 내용을 반환시킨다.
		return board;
	}

	@Override
	public boolean updateBoardModifyById(RequestModifyBoardVO requestModifyBoardVO) {
		
		BoardVO board = this.boardDao.selectBoardById(requestModifyBoardVO.getId());
		
		// Spring --> Controller가 아닌 영역에서 Session을 가져오기 위한 방법 제공.
		MemberVO loginUser = SessionUtil.getLoginObject();
		if ( ! board.getEmail().equals( loginUser.getEmail() )) {
			throw new HelloSpringException("잘못된 접근입니다.", "error/403");
		}
		
		int updateCount = this.boardDao.updateBoardModifyById(requestModifyBoardVO);
		
		if (updateCount == 0) {
			throw new HelloSpringException(requestModifyBoardVO.getId() + " 게시글은 존재하지 않습니다.", "error/404");
		}
		
		return updateCount > 0;
	}

	@Override
	public boolean deleteBoardById(String id) {
		
		BoardVO board = this.boardDao.selectBoardById(id);
		MemberVO loginUser = SessionUtil.getLoginObject();
		if ( ! board.getEmail().equals( loginUser.getEmail() )) {
			throw new HelloSpringException("잘못된 접근입니다.", "error/403");
		}
		
		
		
		int deleteCount = this.boardDao.deleteBoardById(id);
		if (deleteCount == 0) {
			throw new HelloSpringException(id + " 게시글은 존재하지 않습니다.", "error/404");
		}
		
		return deleteCount > 0;
	}

	@Override
	public List<BoardVO> readAllBoardListForExcel() {
		return this.boardDao.selectBoardListForExcel();
	}

}
