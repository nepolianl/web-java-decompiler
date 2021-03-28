package com.jd.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Test {

	public static void main(String[] args) {
		String[] arr = {
				"org.apache.commons.codec.language.bm", 
				"com.neps.test", 
				"org.apache.commons.codec.cli", 
				"com.neps.predix.blobstore.blobstoreversioning", 
				"org.apache.commons.codec.net", 
				"org.apache.commons.codec.binary", 
				"org.apache.commons.codec.language", 
				"org.apache.commons.codec", 
				"com.neps.predix.blobstore.myblobstore.service", 
				"org.apache.commons.codec.digest"};
		
		Arrays.sort(arr);
		
		Arrays.asList(arr).forEach(System.out::println);
		System.out.println();
		
		
		Map<String, List<String>> group = Arrays.asList(arr)
				.stream()
				.collect(Collectors.groupingBy(s -> {
					String[] r = s.split("\\.");
					return r[0];
				}, Collectors.mapping(s -> s, Collectors.toList())));
		//System.out.println(group);
		
		Test t = new Test();
		
		MyTree root = new MyTree("root", null, null);
		int cursor = 1, max = 0;
		for (String s : arr) {
			String[] r = s.split("\\.");
			int len = r.length;
			if (len > max) max = len;
		}
		//t.groupBy(Arrays.asList(arr), root, cursor, max);
		SimpleTree tree = new SimpleTree("root", null, null);
		//t.simpleTree(Arrays.asList(arr), tree, cursor, max);
		t.simpleTree2(Arrays.asList(arr), tree, cursor, max);
		//SimpleTree ct = t.simpleTree3(Arrays.asList(arr), cursor, max);
		System.out.println();
		t.flattenTree(tree.getChildren().get(0));
		System.out.println();
		for (String s : arr) {
			String[] r = s.split("\\.");
			Queue<String> queue = new LinkedList<String>(Arrays.asList(r));
			//List<String> foundList = t.filterBy(queue, Arrays.asList(arr), new StringBuilder(), 0);
			//System.out.println(foundList);
		}
	}

	private void groupBy(List<String> list, MyTree tree, int cursor, int max) {
		Map<String, List<String>> map = this.filter(list, cursor);
		//map.entrySet().forEach(e -> this.groupBy(e.getValue(), ++cursor));
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			// if value is 1 and cursor reaches max return
			List<String> subList = e.getValue();
			String name = e.getKey();
			if (subList.size() == 1 && name.equals(subList.get(0))) {
				tree.addChild(name);
				//MyTree child = new MyTree(key, parent, null);
			} else {
				//tree.addChild(child);;
				MyTree parent = new MyTree(name, tree, null);
				this.groupBy(e.getValue(), parent, ++cursor, max);
			}
			//if (cursor > max) return;
			//this.groupBy(e.getValue(), ++cursor, max);
		}
	}
	
	private void flattenTree(SimpleTree tree) {
		List<SimpleTree> children = tree.getChildren();
		if (children != null && children.size() == 1) {
			tree = children.get(0); // set to parent
		} else {
			if (children != null) {
				for (SimpleTree child : children) {
					this.flattenTree(child);
				}
			}
		}
		this.flattenTree(tree);
	}
	
	private void simpleTree2(List<String> list, SimpleTree tree, int cursor, int max) {
		Map<String, List<String>> map = this.filter(list, cursor);
		//map.entrySet().forEach(e -> this.groupBy(e.getValue(), ++cursor));
		
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			// if value is 1 and cursor reaches max return
			List<String> subList = e.getValue();
			String name = e.getKey();
			
			if (subList.size() == 1 && name.equals(subList.get(0))) {
				SimpleTree child = new SimpleTree(name, tree.getName(), null);
				tree.addChild(child);
				//MyTree child = new MyTree(key, parent, null);
			} else {
				SimpleTree parent = new SimpleTree(name, tree.getName(), null);
				tree.addChild(parent);;
				this.simpleTree2(e.getValue(), parent, ++cursor, max);
			}
			//if (cursor > max) return;
			//this.groupBy(e.getValue(), ++cursor, max);
		}
	}
	
	private SimpleTree simpleTree3(List<String> list, int cursor, int max) {
		Map<String, List<String>> map = this.filter(list, cursor);
		
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			
			List<String> subList = e.getValue();
			String name = e.getKey();
			
			if (subList.size() == 1 && name.equals(subList.get(0))) {
				return new SimpleTree(name);
			} else {
				SimpleTree child = this.simpleTree3(subList, ++cursor, max);
				if (child != null) {
					SimpleTree parent = new SimpleTree(name);
					parent.addChild(child);
					return parent;
				}
			}
		}
		
		return null;
	}
	
	private SimpleTree simpleTree(List<String> list, SimpleTree tree, int cursor, int max) {
		Map<String, List<String>> map = this.filter(list, cursor);
		
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			// if value is 1 and cursor reaches max return
			List<String> subList = e.getValue();
			String name = e.getKey();
			if (subList.size() == 1 && name.equals(subList.get(0))) {
				//tree.addChild(name);
				//return name;
				return new SimpleTree(name, null, null);
			} else {
				//SimpleTree parent = new SimpleTree(name, tree.getName(), null);
				SimpleTree child = this.simpleTree(e.getValue(), tree, ++cursor, max);
				//String child = this.simpleTree(e.getValue(), tree, ++cursor, max);
				if (child != null) {
					// 1. Create parent
					// 2. attach child to parent
					// 3. attach parent to root
					SimpleTree parent = new SimpleTree(name, tree.getName(), null);
					tree.addChild(parent);
					
					parent.addChild(child);
					//return parent;
					
				//} else {
				//	this.simpleTree(e.getValue(), tree, cursor, max);
					//return null;
					//System.out.println();
					//return this.simpleTree(e.getValue(), tree, cursor, max);
				}
				//return null;
				//this.simpleTree(e.getValue(), tree, cursor, max);
				//return this.simpleTree(e.getValue(), tree, cursor, max);
			}
			//if (cursor > max) return;
			//this.groupBy(e.getValue(), ++cursor, max);
		}
		
		return null;
	}
	
	private Map<String, List<String>> filter(List<String> list, int cursor) {
		Map<String, List<String>> map = list.stream()
				.collect(Collectors.groupingBy(s -> this.searchQuery(s, cursor), Collectors.mapping(Function.identity(), Collectors.toList())));
		return map;
	}
	
	private String searchQuery(String s, int cursor) {
		String[] arr = s.split("\\.");
		cursor = cursor > arr.length ? arr.length : cursor;
		List<String> subList = Arrays.asList(arr).subList(0, cursor);
		String searchQuery = String.join(".", subList);
		//String searchQuery = IntStream.range(0, cursor).boxed().sequential().map(i -> arr[i]).collect(Collectors.joining("."));
		return searchQuery;
	}
	
	
	private List<String> filterBy(Queue<String> queue, List<String> list, StringBuilder search, MyTree tree) {
		search.append(queue.poll()).append(".");
		
		List<String> subList = list.stream().filter(s -> s.startsWith(search.toString())).collect(Collectors.toList());
		if (!subList.isEmpty()) {
			//if (tree == null) tree = new MyTree(search.toString(), null, subList);
		}
		
		
		if (subList == null || subList.isEmpty()) {
			System.out.println();
		}
		
		
		
		return null;
	}
	
}

@Getter
@Setter
@AllArgsConstructor
class MyTree {
	private String name;
	private MyTree parent;
	private List<MyTree> children;
	
	public void addChild(MyTree child) {
		if (children == null) {
			children = new ArrayList<MyTree>();
		}
		children.add(child);
	}
	
	public void addChild(String name) {
		if (children == null) {
			children = new ArrayList<MyTree>();
		}
		children.add(new MyTree(name, this, null));
	}
}

@Getter
@Setter
@AllArgsConstructor
class SimpleTree {
	private String name;
	private String parent;
	private List<SimpleTree> children;
	
	public SimpleTree(String name) {
		this.name = name;
	}
	
	public void addChild(SimpleTree child) {
		if (children == null) {
			children = new ArrayList<SimpleTree>();
		}
		child.parent = this.name;
		children.add(child);
	}
}
