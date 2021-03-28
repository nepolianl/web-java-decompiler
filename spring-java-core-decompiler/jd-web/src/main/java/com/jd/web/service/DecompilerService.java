package com.jd.web.service;

import org.springframework.web.multipart.MultipartFile;

import com.jd.web.model.Response;

public interface DecompilerService {
	Response uploadFiles(MultipartFile[] files);
}
