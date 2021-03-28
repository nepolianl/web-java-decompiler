package com.jd.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jd.core.v1.api.loader.Loader;
import com.jd.web.decompiler.loader.JarLoader;
import com.jd.web.decompiler.loader.JarLoaderImpl;
import com.jd.web.decompiler.loader.MultipartLoader;
import com.jd.web.decompiler.loader.MultipartLoaderImpl;
import com.jd.web.decompiler.loader.ZipLoader;
import com.jd.web.model.ClassType;
import com.jd.web.model.DataModel;
import com.jd.web.model.Response;
import com.jd.web.model.Type;
import com.jd.web.service.DecompilerService;
import com.jd.web.service.FileSourceService;
import com.jd.web.utils.DecompilerUtils;
import com.jd.web.utils.IconUtils;
import com.jd.web.utils.Utils;

@Service
public class DecompilerServiceImpl implements DecompilerService {
	
	@Autowired
	private FileSourceService service;

	@Override
	public Response uploadFiles(MultipartFile[] files) {
		//Map<String, List<ClassType>> map = this.decompile(files);
		
		// Map to tree list
		//List<ClassType> treeList = map.entrySet()
			//	.stream().map(e -> this.buildPackageTree(e.getKey(), e.getValue()))
				//.collect(Collectors.toList());
		List<ClassType> treeList = this.decompile(files);
		return new Response(treeList);
	}
	
	private ClassType buildPackageTree(String packageName, List<ClassType> treeList) {
		ClassType pck = new ClassType(packageName, IconUtils.getIcon(0));
		
		treeList.forEach(pck::addChild);
		return pck;
	}
	
	private List<ClassType> decompile(MultipartFile[] files) {
		MultipartLoader multipartLoader = new MultipartLoaderImpl();
		JarLoader jarLoader = new JarLoaderImpl();
		
		// Categorize the files into loaders
		for (MultipartFile part : files) {
			if (part.getOriginalFilename().endsWith(".class")) {
				multipartLoader.addPart(part);
			}
			if (part.getOriginalFilename().endsWith(".jar")) {
				jarLoader.addJar(part);
			}
		}
		
		// Decompile class files
		Map<String, List<ClassType>> map = this.decompileFiles(multipartLoader);
		
		List<ClassType> treeList = new ArrayList<ClassType>();
		// Sort the values by package name
		ClassType classType = this.toPackageTree(map, "Decompiled List");
		treeList.add(classType);
		
		// Decompile Jars
		if (jarLoader.getJars() != null && jarLoader.getJars().length > 0) {
			treeList.addAll(this.decompileJars(jarLoader));
			
		}
		
		return treeList;
	}
	
	private Map<String, List<ClassType>> decompileFiles(MultipartLoader loader) {
		return Arrays.asList(loader.getInternalTypes())
				.stream()
				.map(internalTypeName -> this.decompile(internalTypeName, loader))
				.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
	}
	
	private List<ClassType> decompileJars(JarLoader loader) {	
		return loader.getMap().entrySet()
				.stream()
				.map(e -> {
					Map<String, List<ClassType>> map = this.decompileJar(e.getValue());
					return this.toPackageTree(map, e.getKey());
				})
				.collect(Collectors.toList());
	}
	
	private Map<String, List<ClassType>> decompileJar(ZipLoader loader) {
		return loader.getMap().keySet()
				.stream()
				.map(path -> {
					if (path.endsWith(".class") && (path.indexOf('$') == -1)) {
		                String internalTypeName = path.substring(0, path.length() - 6);
		                return this.decompile(internalTypeName, loader);
		            }
					return null; // TODO for text, xml, property file support
					
				})
				.filter(Objects::nonNull)
				.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
	}
	
	private Map.Entry<String, ClassType> decompile(String internalTypeName, Loader loader) {
		// Decompile into source
		String htmlText = DecompilerUtils.decompile(internalTypeName, loader);
		
		// Visit class attributes and members
		Type type = DecompilerUtils.readClassAttributes(internalTypeName, loader);
		
		// Save data into database
		Integer sourceId = this.service.addSource(new DataModel(type.getDisplayTypeName()+".class", type.getDisplayPackageName(), type.getDisplayTypeName(), htmlText));
		
		// Generate class tree model for class members
		ClassType classTree = DecompilerUtils.toClassTree(type, sourceId);
		
		return Map.entry(type.getDisplayPackageName(), classTree);
	}
	
	private ClassType toPackageTree(Map<String, List<ClassType>> map, String displayName) {
		List<String> packageNames = map.keySet().stream().sorted().collect(Collectors.toList());
		ClassType classTree = Utils.toPackageTree(packageNames, displayName);
		
		for (Map.Entry<String, List<ClassType>> entry : map.entrySet()) {
			String packageName = entry.getKey();
			List<ClassType> children = entry.getValue();
			
			ClassType parent = Utils.findNode(classTree, packageName);
			parent.getChildren().addAll(children);
		}
		
		ClassType tree = Utils.flatternTree(classTree);
		return tree;
	}
}
