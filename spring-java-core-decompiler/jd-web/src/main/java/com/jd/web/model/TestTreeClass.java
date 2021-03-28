package com.jd.web.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestTreeClass {

	public static void main(String[] args) throws IOException {
		TestTreeClass obj = new TestTreeClass();
		
		List<TreeClass> list = new ArrayList<TreeClass>();
		list.add(obj.getTree1());
		list.add(obj.getTree2());
		list.add(obj.getTree3());
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		System.out.println(json);
		
	}
	
	private TreeClass getTree1() {
		TreeClass tc = new TreeClass();
		tc.setName("com.neps");
		tc.setTitle("package");
		tc.setExpand(true);
		tc.setSelected(false);
		
		List<TreeClass> nodes = new ArrayList<TreeClass>();
		tc.setNodes(nodes);
		
		// Class file 
		TreeClass file = this.getClassFile();
		nodes.add(file);
		// Class
		
		nodes = new ArrayList<TreeClass>();
		file.setNodes(nodes);
		TreeClass clz = this.getClazz();
		nodes.add(clz);
		
		// method
		nodes = new ArrayList<TreeClass>();
		clz.setNodes(nodes);
		nodes.addAll(this.getMethods());
		// variable
		nodes.addAll(this.getVariables());
		
		return tc;
	}
	
	private TreeClass getTree2() {
		TreeClass tc = new TreeClass();
		tc.setName("com.neps2");
		tc.setTitle("package");
		tc.setExpand(true);
		tc.setSelected(false);
		
		List<TreeClass> nodes = new ArrayList<TreeClass>();
		tc.setNodes(nodes);
		
		// Class file 
		TreeClass file = this.getClassFile();
		nodes.add(file);
		// Class
		
		nodes = new ArrayList<TreeClass>();
		file.setNodes(nodes);
		TreeClass clz = this.getClazz();
		clz.setExpand(false);
		nodes.add(clz);
		
		// method
		nodes = new ArrayList<TreeClass>();
		clz.setNodes(nodes);
		nodes.addAll(this.getMethods());
		// variable
		nodes.addAll(this.getVariables());
		
		return tc;
	}
	
	private TreeClass getTree3() {
		TreeClass tc = new TreeClass();
		tc.setName("com.neps2");
		tc.setTitle("package");
		tc.setExpand(true);
		tc.setSelected(false);
		
		List<TreeClass> nodes = new ArrayList<TreeClass>();
		tc.setNodes(nodes);
		
		// Class file 
		TreeClass file = this.getClassFile();
		file.setName("ContactServiceImpl.class");
		nodes.add(file);
		// Class
		
		nodes = new ArrayList<TreeClass>();
		file.setNodes(nodes);
		TreeClass clz = this.getClazz();
		clz.setName("ContactServiceImpl");
		clz.setExpand(false);
		nodes.add(clz);
		
		// method
		nodes = new ArrayList<TreeClass>();
		clz.setNodes(nodes);
		nodes.addAll(this.getMethods());
		// variable
		nodes.addAll(this.getVariables());
		
		return tc;
	}

	private TreeClass getClassFile() {
		TreeClass node = new TreeClass();
		node.setName("Utils.class");
		node.setTitle("file");
		node.setExpand(true);
		
		return node;
	}
	
	
	private TreeClass getClazz() {
		TreeClass node = new TreeClass();
		node.setName("Utils");
		node.setTitle("class");
		node.setExpand(true);
		
		return node;
	}
	
	private List<TreeClass> getMethods() {
		List<TreeClass> nodes = new ArrayList<TreeClass>();
				
		TreeClass node = new TreeClass();
		nodes.add(node);
		
		node.setName("toString(int)");
		node.setTitle("method");
		node.setType("public");
		
		node = new TreeClass();
		nodes.add(node);
		
		node.setName("display(String)");
		node.setTitle("method");
		node.setType("protected");
		
		return nodes;
	}
	
	private List<TreeClass> getVariables() {
		List<TreeClass> nodes = new ArrayList<TreeClass>();
		
		TreeClass node = new TreeClass();
		nodes.add(node);
		
		node.setName("number: int");
		node.setTitle("field");
		node.setType("private");
		
		node = new TreeClass();
		nodes.add(node);
		
		node.setName("name: String");
		node.setTitle("field");
		node.setType("protected");
		
		node = new TreeClass();
		nodes.add(node);
		
		node.setName("accessFlag: boolean");
		node.setTitle("field");
		node.setType("public");
		
		return nodes;
	}
}
