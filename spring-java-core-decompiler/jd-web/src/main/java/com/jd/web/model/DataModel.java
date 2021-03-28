package com.jd.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DataModel {
	private String fileName;
	private String packageName;
	private String className;
	private String htmlText;
}
