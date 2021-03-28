package com.jd.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.web.entity.FileSource;
import com.jd.web.model.DataModel;
import com.jd.web.model.SourceVo;
import com.jd.web.repository.FileSourceRepository;
import com.jd.web.service.FileSourceService;

@Service
public class FileSourceServiceImpl implements FileSourceService {

	@Autowired
	private FileSourceRepository repository;
	
	@Override
	public Integer addSource(DataModel model) {
		FileSource source = new FileSource();
		source.setClassName(model.getClassName());
		source.setPackageName(model.getPackageName());
		source.setFileName(model.getFileName());
		source.setHtmlText(model.getHtmlText());
		
		this.repository.save(source);
		return source.getSourceId();
	}

	@Override
	public SourceVo getSource(Integer sourceId) {
		FileSource source = this.repository.findById(sourceId).get();
		return source != null ? new SourceVo(sourceId, source.getHtmlText()) : null;
	}

	@Override
	public SourceVo getSourceBy(String packageName, String className) {
		FileSource source = this.repository.findByPackageNameAndClassName(packageName, className);
		return source != null ? new SourceVo(source.getSourceId(), source.getHtmlText()) : null;
	}

}
