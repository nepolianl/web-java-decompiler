package com.jd.web.utils;

import com.jd.core.v1.ClassFileToJavaSourceDecompiler;
import com.jd.core.v1.api.loader.Loader;
import com.jd.web.decompiler.ClassFilePrinter;
import com.jd.web.model.ClassType;
import com.jd.web.model.Type;
import com.jd.web.service.type.ClassMetadataProvider;

public class DecompilerUtils {
	private static final ClassFileToJavaSourceDecompiler DECOMPILER = new ClassFileToJavaSourceDecompiler();

	public static String decompile(String internalTypeName, Loader loader) {
		try {
			ClassFilePrinter printer = new ClassFilePrinter();
			DECOMPILER.decompile(loader, printer, internalTypeName);
			
			return printer.getStringBuffer().toString(); // TODO, need to convert as html
		} catch (Exception e) {
			throw new RuntimeException("Unable to decompile internal type: " + internalTypeName, e);
		}
	}
	
	public static Type readClassAttributes(String internalTypeName, Loader loader) {
		try {
			Type type = ClassMetadataProvider.readMetadata(internalTypeName, loader);
			return type;
		} catch (Exception e) {
			throw new RuntimeException("Could not visit internal type: " + internalTypeName, e);
		}
	}
	
	public static ClassType toClassTree(Type type, Integer sourceId) {
		ClassType clz = new ClassType(type.getDisplayTypeName(), IconUtils.getIcon(type.getFlags()), sourceId);
		clz.setClassName(type.getDisplayTypeName());
		clz.setPackageName(type.getDisplayPackageName());
		
		addFields(type, clz, sourceId);
		addMethods(type, clz, sourceId);
		addInnerClass(type, clz, sourceId);
		
		ClassType tree = new ClassType(type.getDisplayTypeName() + ".class", IconUtils.getIcon(type.getFlags()), sourceId);
		tree.addChild(clz);
		return tree;
	}
	
	private static void addFields(Type type, ClassType clz, Integer sourceId) {
		type.getFields().forEach(f -> {
			ClassType node = new ClassType(f.getDisplayName(), IconUtils.getIcon(f.getFlags()), sourceId);
			node.setClassName(clz.getClassName());
			node.setPackageName(clz.getPackageName());
			
			clz.addChild(node);
		});
	}
	
	private static void addMethods(Type type, ClassType clz, Integer sourceId) {
		type.getMethods().forEach(m -> {
			ClassType node = new ClassType(m.getDisplayName(), IconUtils.getIcon(m.getFlags()), sourceId);
			node.setClassName(clz.getClassName());
			node.setPackageName(clz.getPackageName());
			
			clz.addChild(node);
		});
	}
	
	private static void addInnerClass(Type type, ClassType clz, Integer sourceId) {
		if (type.getInnerTypes() != null && !type.getInnerTypes().isEmpty()) {
			
			for (Type innerType : type.getInnerTypes()) {
				ClassType innerClz = new ClassType(innerType.getDisplayInnerTypeName(), IconUtils.getIcon(innerType.getFlags()), sourceId);
				innerClz.setClassName(clz.getClassName());
				innerClz.setPackageName(clz.getPackageName());
				
				clz.addChild(innerClz);
				
				addFields(innerType, innerClz, sourceId);
				addMethods(innerType, innerClz, sourceId);
				
				if (innerType.getInnerTypes() != null && !innerType.getInnerTypes().isEmpty()) {
					addInnerClass(innerType, innerClz, sourceId);
				}
			}
		}
	}
}
