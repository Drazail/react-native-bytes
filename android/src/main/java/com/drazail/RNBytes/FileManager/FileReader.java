package com.drazail.RNBytes.FileManager;

import com.drazail.RNBytes.Utils.ReferenceMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class FileReader {

    public RandomAccessFile RAF;
    private File file;
    public String id;

    public FileReader(String path) throws IOException {
        this.id = UUID.randomUUID().toString();
        this.file = new File(path);
        this.RAF = new RandomAccessFile(this.file, "r");
        if (!this.file.exists()) throw new FileNotFoundException();
        if (this.file.isDirectory()) throw new IOException("path points to a directory");
    }

    public void close() throws IOException {
        this.RAF.close();
        ReferenceMap.removeReader(this.id);
    }

    public void read(byte[] buffer, int off, int len) throws IOException {
        this.RAF.read(buffer,off, len);
    }

    public void readFully(byte[] buffer, int off, int len) throws IOException {
        this.RAF.readFully(buffer, off, len);
    }

    public long getFileSize() {
        return this.file.length();
    }

    public String getAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    public void rm() {
        this.file.delete();
    }

    /**
     * @param startingIndex start reading from this byte (including the byte itself)
     * @param finalIndex    last byte to be read (including the byte itself)
     * @return byte[] of all bytes read
     * @throws IOException IOException
     */
    public byte[] toByteArray(int startingIndex, int finalIndex) throws IOException {

        byte[] buffer = new byte[finalIndex - startingIndex + 1];

        this.RAF.seek(startingIndex);
        this.RAF.read(buffer);

        return buffer;

    }
}
