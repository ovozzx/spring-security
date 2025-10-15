package com.ktdsuniversity.edu.reply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.common.exceptions.AjaxException;
import com.ktdsuniversity.edu.common.exceptions.HelloSpringException;
import com.ktdsuniversity.edu.common.util.SessionUtil;
import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.dao.FileGroupDao;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.vo.FileGroupVO;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.reply.dao.ReplyDao;
import com.ktdsuniversity.edu.reply.service.ReplyService;
import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateOrUpdateReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private MultipartFileHandler multipartFileHandler;
	
    @Autowired
    private ReplyDao replyDao;
    
    @Autowired
	private FileGroupDao fileGroupDao;
	
	@Autowired
	private FileDao fileDao;

	@Transactional
	@Override
	public ReplyVO createOrUpdateReply(RequestCreateOrUpdateReplyVO requestCreateOrUpdateReplyVO) {
		
		FileVO uploadFile = this.multipartFileHandler.upload(requestCreateOrUpdateReplyVO.getReplyAttachFile());
		if (uploadFile != null) {
			// 1. File Group Insert
			FileGroupVO fileGroupVO = new FileGroupVO();
			fileGroupVO.setFileCount(1);
			int insertGroupCount = this.fileGroupDao.insertFileGroup(fileGroupVO);
			
			// 2. File Insert
			uploadFile.setFileGroupId(fileGroupVO.getFileGroupId());
			int insertFileCount = this.fileDao.insertFile(uploadFile);
			
			requestCreateOrUpdateReplyVO.setFileGroupId(fileGroupVO.getFileGroupId());
		}
		
		if (requestCreateOrUpdateReplyVO.getReplyId() == null) {
			int insertNewReplyCount = this.replyDao.insertNewReply(requestCreateOrUpdateReplyVO);
		}
		else {
			// 댓글 첨부파일 버그 수정 시작
			ReplyVO replyVO = this.replyDao.selectReplyByReplyId(
					requestCreateOrUpdateReplyVO.getReplyId());
			
			// 내가 쓴 것인지 확인 필요.
			if ( ! replyVO.getEmail().equals(requestCreateOrUpdateReplyVO.getEmail())) {
				throw new AjaxException("잘못된 접근입니다.", HttpStatus.NOT_FOUND);
			}
			
			String deleteFileId = requestCreateOrUpdateReplyVO.getDeleteFileId();
			if (uploadFile == null && deleteFileId != null) {
				requestCreateOrUpdateReplyVO.setFileGroupId(null);
			}
			else if (uploadFile == null && deleteFileId == null) {
				requestCreateOrUpdateReplyVO.setFileGroupId(replyVO.getFileGroupId());
			}
			// 댓글 첨부파일 버그 수정 끝
			
			// 댓글 수정.
			int updateReplyCount = this.replyDao.updateReply(requestCreateOrUpdateReplyVO);
			// 원래 첨부되었던 파일 정보 삭제.
			int deleteFileGroupCount = this.fileGroupDao.deleteGroup(requestCreateOrUpdateReplyVO.getDeleteFileId());
			int deleteFileCount = this.fileDao.deleteFile(requestCreateOrUpdateReplyVO.getDeleteFileId());
		}
		ReplyVO insertedReply = this.replyDao.selectReplyByReplyId(
										requestCreateOrUpdateReplyVO.getReplyId());
		return insertedReply;
	}

	@Override
	public List<ReplyVO> readReplyListByBoardId(String boardId) {
		List<ReplyVO> replyList = this.replyDao.selectReplyListByBoardId(boardId);
		return replyList;
	}
	
	@Transactional
	@Override
	public int updateReplyRecommendByReplyId(String replyId) {
		String loginUser = SessionUtil.getLoginObject().getEmail();
		
		// replyId로 댓글 정보 조회.
		ReplyVO replyVO = this.replyDao.selectReplyByReplyId(replyId);
		if (replyVO == null) {
			throw new AjaxException("삭제된 댓글입니다.", HttpStatus.NOT_FOUND);
		}
		
		// 댓글 작성자와 로그인 작성자가 동일한지 검사.
		// 같다면 예외를 던짐.
		if (loginUser.equals(replyVO.getEmail())) {
			throw new AjaxException("잘못된 접근입니다.", HttpStatus.NOT_FOUND);
		}
		
		// 다르다면 추천수를 증가
		int updateCount = this.replyDao.updateReplyRecommendByReplyId(replyId);
		
		// 다시 replyId로 댓글 정보 조회.
		// 조회된 댓글의 추천수만 반환
		return this.replyDao.selectReplyByReplyId(replyId).getRecommendCnt();
	}

	@Transactional
	@Override
	public boolean deleteReplyByReplyId(String replyId) {
		String loginUser = SessionUtil.getLoginObject().getEmail();
		
		// replyId로 댓글 정보 조회.
		ReplyVO replyVO = this.replyDao.selectReplyByReplyId(replyId);
		if (replyVO == null) {
			throw new AjaxException("이미 삭제된 댓글입니다.", HttpStatus.NOT_FOUND);
		}
		
		// 댓글 작성자와 로그인 작성자가 동일한지 검사.
		// 다르다면 예외를 던짐.
		if (!loginUser.equals(replyVO.getEmail())) {
			throw new AjaxException("잘못된 접근입니다.", HttpStatus.NOT_FOUND);
		}
		
		// 같다면 삭제
		return this.replyDao.deleteReplyByReplyId(replyId) > 0;
	}
}