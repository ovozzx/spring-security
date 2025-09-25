package com.ktdsuniversity.edu.file.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao fileDao;

}