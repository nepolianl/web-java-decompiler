package com.jd.web.service;

import com.jd.core.v1.api.loader.Loader;
import com.jd.web.model.FileMetadata;

public interface FileMetadataService {
	FileMetadata getAsHtmlTextAndMetdata(String internalType, Loader loader, String text);
}
