package com.jd.web.decompiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.jd.web.decompiler.ClassFilePrinter.DeclarationData;
import com.jd.web.decompiler.ClassFilePrinter.ReferenceData;
import com.jd.web.decompiler.ClassFilePrinter.StringData;

public class ParseDeclarations {
 
	public static void parse(HashMap<String, DeclarationData> data) {
		 HashMap<String, String> parameters = new HashMap<>();
		 parameters.put("highlightPattern", "ab");
	        parameters.put("highlightFlags", "s");

	        parameters.put("highlightScope", null);
	        
		data.entrySet().forEach(e -> {
			String k = e.getKey();
			DeclarationData d = e.getValue();
			if (d.isAType() || d.isAConstructor()) {
				System.out.println(d.typeName);
			}
			if (d.isAField() || d.isAMethod()) {
				System.out.println(d.name);
			}
		});
	}
	
	public static void parseTypeDeclarations(TreeMap<Integer, DeclarationData> map) {
		//TreeMap<Integer, DeclarationData> typeDeclarations = new TreeMap<>();
		map.entrySet().forEach(e -> {
			DeclarationData d = e.getValue();
			
			if (d.isAType() || d.isAConstructor()) {
				System.out.println(d.typeName + " - " + d.name);
			}
			if (d.isAField() || d.isAMethod()) {
				System.out.println(d.typeName + " - " + d.name);
			}
		});
	}
	
	public static void parseRef(ArrayList<ReferenceData> ref) {
		ref.forEach( r -> {
			if (r.isAType() || r.isAConstructor()) {
				System.out.println(r.name +", " + r.typeName +", " + r.owner +", " + r.descriptor);	
			}
			
			if (r.isAField() || r.isAMethod()) {
				System.out.println(r.name +", " + r.typeName +", " + r.owner +", " + r.descriptor);	
			}
			
		});
	}
	
	public static void parseStrings(ArrayList<StringData> strings) {
		strings.forEach(s -> {
			System.out.println(s.text +", " + s.owner);
		});
	}
	
	public static boolean matchScope(String scope, String type) {
        if ((scope == null) || scope.isEmpty())
            return true;
        if (scope.charAt(0) == '*')
            return type.endsWith(scope.substring(1)) || type.equals(scope.substring(2));
        return type.equals(scope);
    }
	
	public static String getMostInnerTypeName(String typeName) {
        int lastPackageSeparatorIndex = typeName.lastIndexOf('/') + 1;
        int lastTypeNameSeparatorIndex = typeName.lastIndexOf('$') + 1;
        int lastIndex = Math.max(lastPackageSeparatorIndex, lastTypeNameSeparatorIndex);
        return typeName.substring(lastIndex);
    }
	
}
