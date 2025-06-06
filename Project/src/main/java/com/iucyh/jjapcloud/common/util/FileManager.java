package com.iucyh.jjapcloud.common.util;

import com.iucyh.jjapcloud.common.exception.ServiceException;
import com.iucyh.jjapcloud.common.wrapper.LimitedInputStream;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Component
public class FileManager {

    private static final String fileDir = "/Users/iucyh/jjapcloud-file/";
    private static final Tika tika = new Tika();

    public FileManager() {
        File dir = new File(fileDir);
        if(!dir.exists()) {
            createDir(dir);
        }
    }

    public boolean isCorrectMimeType(MultipartFile file, String type) {
        String mimeType = "";

        try {
            mimeType = tika.detect(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return mimeType.contains(type);
    }

    public String uploadFile(MultipartFile file, String root) {
        File rootDir = new File(fileDir + root);
        if(!rootDir.exists()) {
            createDir(rootDir);
        }

        String uniqueName = getUniqueName(file.getOriginalFilename());
        String fullPath = getFullPath(root, uniqueName);

        try {
            file.transferTo(new File(fullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uniqueName;
    }

    public File getFile(String root, String name) {
        return new File(fileDir + root + "/" + name);
    }

    public LimitedInputStream getLimitedInputStream(File file, long start, long end) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            inputStream.skip(start);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new LimitedInputStream(inputStream, end - start + 1);
    }

    private void createDir(File fileDir) {
        boolean success = fileDir.mkdirs();
        if (!success) {
            throw new RuntimeException("Create file dir failed");
        }
    }

    private String getUniqueName(String originalName) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalName);

        return uuid + "." + ext;
    }

    private String extractExt(String originalName) {
        int pos = originalName.lastIndexOf(".");
        return originalName.substring(pos + 1);
    }

    private String getFullPath(String root, String name) {
        return fileDir + root + "/" + name;
    }
}
