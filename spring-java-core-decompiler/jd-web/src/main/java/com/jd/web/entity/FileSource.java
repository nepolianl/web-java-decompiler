package com.jd.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "source", uniqueConstraints = {@UniqueConstraint(columnNames = {"className", "packageName"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileSource {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sourceId;
	
	private String fileName;
	@Column(unique = true)
	private String className;
	@Column(unique = true)
	private String packageName;
	@Lob
	private String htmlText;
}
