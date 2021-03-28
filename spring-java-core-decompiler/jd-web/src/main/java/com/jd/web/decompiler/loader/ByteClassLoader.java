package com.jd.web.decompiler.loader;

public class ByteClassLoader extends ClassLoader {

	public Class<?> defineClass(String name, byte[] b) {
		return defineClass(name, b, 0, b.length);
	}
}
