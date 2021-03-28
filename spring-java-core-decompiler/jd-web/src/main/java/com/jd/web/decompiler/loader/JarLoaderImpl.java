package com.jd.web.decompiler.loader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class JarLoaderImpl implements JarLoader {
	private Map<String, ZipLoader> jars = new HashMap<String, ZipLoader>();
	
	@Override
	public void addJar(MultipartFile part) {
		String fileName = part.getOriginalFilename();
		try {
			ZipLoader loader = new ZipLoader(part.getInputStream());
			this.jars.put(fileName, loader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ZipLoader[] getJars() {
		return this.jars.values().toArray(ZipLoader[]::new);
	}

	@Override
	public Map<String, ZipLoader> getMap() {
		return this.jars;
	}
}
