package com.jd.web.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMain {

	public static void main(String[] args) throws IOException {
		List<TreeNode> list = new ArrayList<TreeNode>();
		
		// Tree - 1
		TreeNode node = new TreeNode();
		node.setName("com.neps.poc.service");
		node.setTitle("package");
		list.add(node);
		
		node = new TreeNode();
		node.setName("ContactService.class");
		node.setTitle("file");
		node.setParent("com.neps.poc.service");
		list.add(node);
		
		node = new TreeNode();
		node.setName("ContactService");
		node.setTitle("interface");
		node.setParent("ContactService.class");
		list.add(node);
		
		node = new TreeNode();
		node.setName("addContact(Contact)");
		node.setTitle("method");
		node.setType("public");
		node.setParent("ContactService");
		list.add(node);
		
		node = new TreeNode();
		node.setName("editContact(Contact)");
		node.setTitle("method");
		node.setType("public");
		node.setParent("ContactService");
		list.add(node);

		node = new TreeNode();
		node.setName("deleteContact(Contact)");
		node.setTitle("method");
		node.setType("protected");
		node.setParent("ContactService");
		list.add(node);
		
		node = new TreeNode();
		node.setName("findByLastName(String)");
		node.setTitle("method");
		node.setType("public");
		node.setParent("ContactService");
		list.add(node);
		
		// Tree-2
		node = new TreeNode();
		node.setName("com.neps.poc.service.impl");
		node.setTitle("package");
		list.add(node);
		
		node = new TreeNode();
		node.setName("ContactServiceImpl.class");
		node.setTitle("file");
		node.setParent("com.neps.poc.service.impl");
		list.add(node);
		
		node = new TreeNode();
		node.setName("ContactServiceImpl");
		node.setTitle("class");
		node.setParent("ContactServiceImpl.class");
		list.add(node);
		
		node = new TreeNode();
		node.setName("addContact(Contact)");
		node.setTitle("method");
		node.setType("public");
		node.setParent("ContactServiceImpl");
		list.add(node);
		
		node = new TreeNode();
		node.setName("editContact(Contact)");
		node.setTitle("method");
		node.setType("public");
		node.setParent("ContactServiceImpl");
		list.add(node);

		node = new TreeNode();
		node.setName("deleteContact(Contact)");
		node.setTitle("method");
		node.setType("protected");
		node.setParent("ContactServiceImpl");
		list.add(node);
		
		node = new TreeNode();
		node.setName("findByLastName(String)");
		node.setTitle("method");
		node.setType("public");
		node.setParent("ContactServiceImpl");
		list.add(node);
		
		node = new TreeNode();
		node.setName("contact: Contact");
		node.setTitle("field");
		node.setType("private");
		node.setParent("ContactServiceImpl");
		list.add(node);
		
		node = new TreeNode();
		node.setName("isDeleted: boolean");
		node.setTitle("field");
		node.setType("protected");
		node.setParent("ContactServiceImpl");
		list.add(node);
		
		node = new TreeNode();
		node.setName("searchCriteria: String");
		node.setTitle("field");
		node.setType("public");
		node.setParent("ContactServiceImpl");
		list.add(node);
		
		// Tree -3
		node = new TreeNode();
		node.setName("com.neps.poc.repository");
		node.setTitle("package");
		list.add(node);
		
		node = new TreeNode();
		node.setName("ContactRepository.class");
		node.setTitle("file");
		node.setParent("com.neps.poc.repository");
		list.add(node);
		
		node = new TreeNode();
		node.setName("ContactRepository");
		node.setTitle("class");
		node.setParent("ContactRepository.class");
		list.add(node);
		
		node = new TreeNode();
		node.setName("save(Contact)");
		node.setTitle("method");
		node.setType("public");
		node.setParent("ContactRepository");
		list.add(node);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		//System.out.println(json);
		
		//Map<String, List<TreeNode>> l2 = list.stream().collect(Collectors.groupingBy(TreeNode::getParent, Collectors.toList()));
		
		//json = mapper.writeValueAsString(l2);
		//System.out.println(json);
		Map<String, TreeNode> map = list.stream().collect(Collectors.toMap(TreeNode::getName, Function.identity()));
		System.out.println(list.size() == map.size());
	}

}
