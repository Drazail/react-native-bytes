package com.drazail.RNBytes.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {
    File file;

    public FileWriter(String path) throws IOException {
        this.file = new File(path);
        if (this.file.exists()) throw new IOException("a file exists at the given path");
        if(this.file.isDirectory()) throw  new IOException("path points to a directory");

    }

    public String writeToFile( byte[] buffer) throws IOException {
        String absolutePath = this.file.getAbsolutePath();
        FileOutputStream fos = new FileOutputStream(absolutePath);
        fos.write(buffer);
        fos.close();
        return absolutePath;
    }


}
