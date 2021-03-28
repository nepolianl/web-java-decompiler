package com.jd.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jd.web.model.Response;
import com.jd.web.model.SourceVo;
import com.jd.web.service.DecompilerService;
import com.jd.web.service.FileSourceService;

@RestController
@RequestMapping("/jd/api/")
public class JDController {

	@Autowired
	private DecompilerService service;
	
	@Autowired
	private FileSourceService source;
	
	@PostMapping("/upload")
	public Response uploadFiles(@RequestParam("files") MultipartFile[] files) {
		return this.service.uploadFiles(files);
	}
	
	@GetMapping("source")
	public SourceVo getSource(@RequestParam("sourceId") Integer sourceId) {
		return this.source.getSource(sourceId);
	}
}
