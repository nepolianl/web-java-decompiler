package com.jd.web.decompiler.loader;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.springframework.web.multipart.MultipartFile;

import com.jd.core.v1.api.loader.Loader;
import com.jd.core.v1.api.loader.LoaderException;

public class MultipartFileLoader implements MetadataLoader {

	private transient Map<String, byte[]> bucket = new ConcurrentHashMap<String, byte[]>();
	
	public MultipartFileLoader(MultipartFile file) {
		this.checkMultipartFile(file);
	}
	
	public MultipartFileLoader(MultipartFile[] files) {
		for (MultipartFile file : files) {
			this.checkMultipartFile(file);
		}
	}
	
	@Override
	public boolean canLoad(String internalTypeName) {
		return this.bucket.containsKey(internalTypeName);
	}

	@Override
	public byte[] load(String internalTypeName) throws LoaderException {
		if (this.canLoad(internalTypeName)) {
			return this.bucket.get(internalTypeName);
		}
		return null;
	}

	@Override
	public Loader getLoader() {
		return this;
	}
	
	@Override
	public String[] getInternalTypes() {
		// Filtering inner classes
		return this.bucket.keySet().stream().filter(internalType -> !internalType.contains("$")).toArray(String[]::new);
		//return this.bucket.keySet().toArray(new String[this.bucket.keySet().size()]);
	}

	private void checkMultipartFile(MultipartFile file) {
		try {
			byte[] bytes = file.getBytes();
			ClassReader classReader = new ClassReader(bytes, 0, bytes.length);
			
			String pathInFile = classReader.getClassName().replace("/", File.separator);
			String classPackagePath = pathInFile.replace("\\", ".");
			
			ByteClassLoader loader = new ByteClassLoader();
			Class<?> cls = loader.defineClass(classPackagePath, bytes);
			String internalTypeName = Type.getInternalName(cls);
			
			this.bucket.put(internalTypeName, bytes);
		} catch (IOException e) {
			throw new RuntimeException("Invalid content or not transferred", e);
		}
	}
}
