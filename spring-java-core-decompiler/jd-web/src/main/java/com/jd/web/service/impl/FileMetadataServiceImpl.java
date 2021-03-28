package com.jd.web.service.impl;

import org.springframework.stereotype.Service;

import com.jd.core.v1.api.loader.Loader;
import com.jd.web.model.FileMetadata;
import com.jd.web.model.TreeModel;
import com.jd.web.model.Type;
import com.jd.web.service.FileMetadataService;
import com.jd.web.service.type.ClassMetadataProvider;

@Service
public class FileMetadataServiceImpl implements FileMetadataService {

	@Override
	public FileMetadata getAsHtmlTextAndMetdata(String internalType, Loader loader, String text) {
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setHtmlText(text);
		try {
			Type type = ClassMetadataProvider.readMetadata(internalType, loader);
			fileMetadata.setTree(this.generateTree(type));
			fileMetadata.setClassName(type.getDisplayTypeName());
		} catch (Exception e) {
			throw new RuntimeException("Could not visit internal type: " + internalType, e);
		}
		
		
		return fileMetadata;
	}
	
	private TreeModel generateTree(Type type) {
		TreeModel tree = new TreeModel(type.getDisplayPackageName(), "package");
		
		TreeModel clzFile = new TreeModel(type.getDisplayTypeName() +".class", "file");
		tree.addChild(clzFile);
		
		TreeModel clz = new TreeModel(type.getDisplayTypeName(), "class");
		clzFile.addChild(clz);
		
		this.addFields(type, clz);
		this.addMethods(type, clz);
		this.addInnerClass(type, clz);
		
		return tree;
	}

	private void addInnerClass(Type type, TreeModel clz) {
		if (type.getInnerTypes() != null && !type.getInnerTypes().isEmpty()) {
			
			for (Type innerType : type.getInnerTypes()) {
				TreeModel innerClz = new TreeModel(innerType.getDisplayInnerTypeName(), "class");
				clz.addChild(innerClz);
				
				this.addFields(innerType, innerClz);
				this.addMethods(innerType, innerClz);
				
				if (innerType.getInnerTypes() != null && !innerType.getInnerTypes().isEmpty()) {
					this.addInnerClass(innerType, innerClz);
				}
			}
		}
	}
	
	private void addFields(Type type, TreeModel clz) {
		type.getFields().forEach(f -> {
			TreeModel node = new TreeModel(f.getDisplayName(), "field", "public");//TODO
			clz.addChild(node);
		});
	}
	
	private void addMethods(Type type, TreeModel clz) {
		type.getMethods().forEach(m -> {
			TreeModel node = new TreeModel(m.getDisplayName(), "method", "public");//TODO
			clz.addChild(node);
		});
	}
	
}
