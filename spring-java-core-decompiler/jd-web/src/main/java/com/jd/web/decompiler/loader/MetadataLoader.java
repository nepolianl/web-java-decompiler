package com.jd.web.decompiler.loader;

import com.jd.core.v1.api.loader.Loader;

public interface MetadataLoader extends Loader {
	Loader getLoader();
	String[] getInternalTypes();
}
