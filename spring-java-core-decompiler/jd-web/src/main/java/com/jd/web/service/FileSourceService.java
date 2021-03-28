package com.jd.web.service;

import com.jd.web.model.DataModel;
import com.jd.web.model.SourceVo;

public interface FileSourceService {
	Integer addSource(DataModel model);
	SourceVo getSource(Integer sourceId);
	SourceVo getSourceBy(String packageName, String className);
}
