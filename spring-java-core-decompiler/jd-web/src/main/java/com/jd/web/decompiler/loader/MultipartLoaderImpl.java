package com.jd.web.decompiler.loader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.springframework.web.multipart.MultipartFile;

import com.jd.core.v1.api.loader.LoaderException;

public class MultipartLoaderImpl implements MultipartLoader {

	private Map<String, byte[]> parts = new HashMap<String, byte[]>();
	
	@Override
	public boolean canLoad(String internalName) {
		return this.parts.containsKey(internalName);
	}

	@Override
	public byte[] load(String internalName) throws LoaderException {
		return this.parts.get(internalName);
	}

	@Override
	public void addPart(MultipartFile part) {
		try {
			byte[] bytes = part.getBytes();
			ClassReader classReader = new ClassReader(bytes, 0, bytes.length);
			
			String pathInFile = classReader.getClassName().replace("/", File.separator);
			String classPackagePath = pathInFile.replace("\\", ".");
			
			ByteClassLoader loader = new ByteClassLoader();
			Class<?> cls = loader.defineClass(classPackagePath, bytes);
			String internalTypeName = Type.getInternalName(cls);
			
			this.parts.put(internalTypeName, bytes);
		} catch (IOException e) {
			throw new RuntimeException("Invalid class file content", e);
		}
	}

	@Override
	public String[] getInternalTypes() {
		return this.parts.keySet().stream().filter(internalType -> !internalType.contains("$")).toArray(String[]::new);
	}

}
