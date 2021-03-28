package com.jd.web.decompiler.loader;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface JarLoader {
	void addJar(MultipartFile part);
	ZipLoader[] getJars();
	Map<String, ZipLoader> getMap();
}
