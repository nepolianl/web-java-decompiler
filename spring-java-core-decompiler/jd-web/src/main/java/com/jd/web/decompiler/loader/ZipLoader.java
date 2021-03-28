package com.jd.web.decompiler.loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.jd.core.v1.api.loader.Loader;
import com.jd.core.v1.api.loader.LoaderException;

public class ZipLoader implements Loader {
    protected HashMap<String, byte[]> map = new HashMap<>();

    public  ZipLoader(InputStream is) throws IOException {
        byte[] buffer = new byte[1024 * 2];

        try (ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                if (ze.isDirectory() == false) {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    int read = zis.read(buffer);

                    while (read > 0) {
                        out.write(buffer, 0, read);
                        read = zis.read(buffer);
                    }

                    map.put(ze.getName(), out.toByteArray());
                }

                ze = zis.getNextEntry();
            }

            zis.closeEntry();
        } catch (IOException e) {
            throw e;
        }
    }

    public HashMap<String, byte[]> getMap() { return map; }

    @Override
    public byte[] load(String internalName) throws LoaderException {
        return map.get(internalName + ".class");
    }

    @Override
    public boolean canLoad(String internalName) {
        return map.containsKey(internalName + ".class");
    }
}
