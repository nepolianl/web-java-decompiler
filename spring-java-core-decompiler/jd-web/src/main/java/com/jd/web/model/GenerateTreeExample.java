package com.jd.web.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GenerateTreeExample {

	public static void main(String[] args) throws IOException {
		List<TreeModel> list = new ArrayList<TreeModel>();
		
		// Tree -1
		TreeModel tree = new TreeModel("com.neps.poc.service", "package");
		list.add(tree);
		
		TreeModel f = new TreeModel("ContactService.class", "file");
		tree.addChild(f);
		
		TreeModel inter = new TreeModel("ContactService", "interface");
		f.addChild(inter);

		inter.addChild(new TreeModel("addContact(Contact)", "method", "public"));
		inter.addChild(new TreeModel("editContact(Contact)", "method", "public"));
		inter.addChild(new TreeModel("deleteContact(Contact)", "method", "protected"));
		inter.addChild(new TreeModel("findByLastName(String)", "method", "public"));

		// Tree -1.1
		f = new TreeModel("BookService.class", "file");
		tree.addChild(f);
		
		inter = new TreeModel("BookService", "interface");
		f.addChild(inter);

		inter.addChild(new TreeModel("addBook(Book)", "method", "public"));
		inter.addChild(new TreeModel("editBook(Book)", "method", "public"));
		inter.addChild(new TreeModel("deleteBook(Book)", "method", "protected"));
		inter.addChild(new TreeModel("findByTitle(String)", "method", "public"));
		
		// Tree -2
		tree = new TreeModel("com.neps.poc.service.impl", "package");
		list.add(tree);
		
		f = new TreeModel("ContactServiceImpl.class", "file");
		tree.addChild(f);
		TreeModel clz = new TreeModel("ContactServiceImpl", "class");
		f.addChild(clz);
		
		clz.addChild(new TreeModel("addContact(Contact)", "method", "public"));
		clz.addChild(new TreeModel("editContact(Contact)", "method", "protected"));
		clz.addChild(new TreeModel("deleteContact(Contact)", "method", "private"));
		clz.addChild(new TreeModel("findByLastName(Contact)", "method", "public"));
		clz.addChild(new TreeModel("isDeleted: boolean", "field", "public"));
		clz.addChild(new TreeModel("searchCriteria: string", "method", "public"));
		clz.addChild(new TreeModel("contactRepository: ContactRepository", "method", "private"));
		
		// Tree - 2.2
		f = new TreeModel("BookServiceImpl.class", "file");
		tree.addChild(f);
		clz = new TreeModel("BookServiceImpl", "class");
		f.addChild(clz);
		
		clz.addChild(new TreeModel("addBook(Book)", "method", "public"));
		clz.addChild(new TreeModel("editBook(Book)", "method", "protected"));
		clz.addChild(new TreeModel("deleteBook(Book)", "method", "private"));
		clz.addChild(new TreeModel("findByTitle(Book)", "method", "public"));
		clz.addChild(new TreeModel("isDeleted: boolean", "field", "public"));
		clz.addChild(new TreeModel("searchCriteria: string", "method", "public"));
		clz.addChild(new TreeModel("bookRepo: BookRepository", "method", "private"));
		
		// Tree - 3
		tree = new TreeModel("com.neps.poc.repository", "package");
		list.add(tree);
		
		f = new TreeModel("ContactRepository.class", "file");
		tree.addChild(f);
		inter = new TreeModel("ContactRepository", "interface");
		f.addChild(inter);
		
		inter.addChild(new TreeModel("save(Contact)", "method", "public"));
		inter.addChild(new TreeModel("findByLastName(String)", "method", "protected"));
		
		// Tree - 3.1
		f = new TreeModel("BookRepository.class", "file");
		tree.addChild(f);
		inter = new TreeModel("BookRepository", "interface");
		f.addChild(inter);
		
		inter.addChild(new TreeModel("save(Book)", "method", "public"));
		inter.addChild(new TreeModel("findByTitle(String)", "method", "protected"));
		
		//System.out.println(list.size() == 3);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		
		System.out.println(json);
		
	}

}
