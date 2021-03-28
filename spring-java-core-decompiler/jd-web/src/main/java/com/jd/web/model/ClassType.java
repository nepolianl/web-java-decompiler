package com.jd.web.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassType {
	private String name;
	private String icon;
	
	private String className;
	private String packageName;
	
	private Integer sourceId;
	
	private List<ClassType> children = new ArrayList<ClassType>();
	
	public ClassType(String name, String icon) {
		this(name, icon, null);
	}
	
	public ClassType(String name, String icon, Integer sourceId) {
		this.name = name;
		this.icon = icon;
		this.sourceId = sourceId;
	}
	
	public void addChild(ClassType model) {
		this.children.add(model);
	}
}
