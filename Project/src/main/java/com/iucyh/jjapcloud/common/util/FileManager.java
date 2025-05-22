package com.iucyh.jjapcloud.common.util;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    public boolean isCorrectMimeType(MultipartFile file, String type) throws IOException {
        String mimeType = tika.detect(file.getInputStream());
        return mimeType.contains(type);
    }

    public String uploadFile(MultipartFile file, String root) throws IOException {
        File rootDir = new File(fileDir + root);
        if(!rootDir.exists()) {
            createDir(rootDir);
        }

        String uniqueName = getUniqueName(file.getOriginalFilename());
        String fullPath = getFullPath(root, uniqueName);

        file.transferTo(new File(fullPath));
        return uniqueName;
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
