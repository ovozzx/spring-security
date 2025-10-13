package com.ktdsuniversity.edu.reply.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.dao.FileGroupDao;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.vo.FileGroupVO;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.reply.dao.ReplyDao;
import com.ktdsuniversity.edu.reply.service.ReplyService;
import com.ktdsuniversity.edu.reply.vo.ReplyVO;
import com.ktdsuniversity.edu.reply.vo.RequestCreateReplyVO;

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
	public ReplyVO createReply(RequestCreateReplyVO requestCreateReplyVO) {
		
		FileVO uploadFile = this.multipartFileHandler.upload(requestCreateReplyVO.getReplyAttachFile());
		if (uploadFile != null) {
			// 1. File Group Insert
			FileGroupVO fileGroupVO = new FileGroupVO();
			fileGroupVO.setFileCount(1);
			int insertGroupCount = this.fileGroupDao.insertFileGroup(fileGroupVO);
			
			// 2. File Insert
			uploadFile.setFileGroupId(fileGroupVO.getFileGroupId());
			int insertFileCount = this.fileDao.insertFile(uploadFile);
			
			requestCreateReplyVO.setFileGroupId(fileGroupVO.getFileGroupId());
		}
		
		int insertNewReplyCount = this.replyDao.insertNewReply(requestCreateReplyVO);
		ReplyVO insertedReply = this.replyDao.selectReplyByReplyId(
										requestCreateReplyVO.getReplyId());
		return insertedReply;
	}

}