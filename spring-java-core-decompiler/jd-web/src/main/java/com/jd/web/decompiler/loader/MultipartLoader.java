package com.jd.web.decompiler.loader;

import org.springframework.web.multipart.MultipartFile;

import com.jd.core.v1.api.loader.Loader;

public interface MultipartLoader extends Loader {
	void addPart(MultipartFile part);
	String[] getInternalTypes();
}
