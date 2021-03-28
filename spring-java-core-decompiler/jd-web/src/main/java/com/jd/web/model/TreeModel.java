package com.jd.web.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeModel {
	private String name;
	private String title;
	private String type;
	
	private String parent;
	private List<TreeModel> children = new ArrayList<TreeModel>();
	
	public TreeModel(String name, String title) {
		this(name, title, null);
	}
	
	public TreeModel(String name, String title, String type) {
		this.name = name;
		this.title = title;
		this.type = type;
	}
	
	public void addChild(TreeModel model) {
		model.parent = this.name;
		this.children.add(model);
	}
}
