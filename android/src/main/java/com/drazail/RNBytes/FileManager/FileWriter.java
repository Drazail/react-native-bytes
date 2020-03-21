package com.drazail.RNBytes.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {
    File file;
    Boolean overWrite;
    public FileWriter(String path, Boolean shoudlOverWrite) throws IOException {
        this.file = new File(path);
        this.overWrite = shoudlOverWrite;
        if(!shoudlOverWrite) {
            if (this.file.exists()) throw new IOException("a file exists at the given path");
        }
        if (this.file.isDirectory()) throw new IOException("path points to a directory");

    }

    public String writeToFile(byte[] buffer, boolean shouldAppend) throws IOException {
        String absolutePath = this.file.getAbsolutePath();
        FileOutputStream fos;
        if(shouldAppend){
            fos = new FileOutputStream(this.file, true);
        }else {
            fos = new FileOutputStream(absolutePath);
        }
        fos.write(buffer);
        fos.close();
        return absolutePath;
    }
}
