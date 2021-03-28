package com.jd.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jd.web.entity.FileSource;

@Repository
public interface FileSourceRepository extends JpaRepository<FileSource, Integer> {
	FileSource findByPackageNameAndClassName(String packageName, String className);
}
