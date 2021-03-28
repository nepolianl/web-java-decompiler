package com.jd.web.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeClass {
	private String name;
	private String title;
	private String type;
	private boolean expand = false;
	private boolean selected = false;
	
	private List<TreeClass> nodes = new ArrayList<TreeClass>();
}
