package com.jd.web.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.jd.web.model.ClassType;

public class Utils {

	public static ClassType toPackageTree(List<String> packageNames, String displayName) {
		ClassType root = new ClassType(displayName, IconUtils.getIcon(0));
		int cursor = 1, max = 0;
		
		for (String name : packageNames) {
			String[] arr = name.split("\\.");
			int len = arr.length;
			
			if (len > max) max = len;
		}
		
		toPackageTree(packageNames, root, cursor, max);
		return root;
	}
	
	public static ClassType findNode(ClassType tree, String nodeName) {
		if (nodeName.equals(tree.getName())) {
			return tree;
		} else {
			List<ClassType> children = tree.getChildren();
			if (children != null && !children.isEmpty()) {
				for (ClassType type : children) {
					ClassType ret = findNode(type, nodeName);
					if (ret != null) return ret;
				}
			}
		}
		return null;
	}
	
	public static ClassType flatternTree(ClassType tree) {
		List<ClassType> children = tree.getChildren();
		if (children != null && children.size() == 1) {
			children.get(0);
		} else {
			for (ClassType type : children) {
				ClassType parent = flatternTree(type);
				if (parent != null) {
					return parent;
				}
			}
		}
		return tree;
	}
	
	private static void toPackageTree(List<String> packageNames, ClassType tree, int cursor, int max) {
		Map<String, List<String>> map = groupBy(packageNames, cursor);
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			List<String> subList = e.getValue();
			String name = e.getKey();
			if (subList.size() == 1 && name.equals(subList.get(0))) {
				ClassType child = new ClassType(name, null);
				child.setPackageName(tree.getName());
				tree.addChild(child);
			} else {
				ClassType parent = new ClassType(name, null);
				parent.setPackageName(tree.getPackageName());
				tree.addChild(parent);;
				toPackageTree(e.getValue(), parent, ++cursor, max);
			}
		}
	}
	
	private static Map<String, List<String>> groupBy(List<String> packageNames, int cursor) {
		return packageNames.stream().filter(Objects::nonNull).collect(
				Collectors.groupingBy(packageName -> packageSubString(packageName, cursor), 
						Collectors.mapping(Function.identity(), Collectors.toList())));
	}
	
	private static String packageSubString(String packageName, int cursor) {
		String[] arr = packageName.split("\\.");
		cursor = cursor > arr.length ? arr.length : cursor;
		
		List<String> subList = Arrays.asList(arr).subList(0, cursor);
		String searchQuery = String.join(".", subList);
		return searchQuery;
	}
}
