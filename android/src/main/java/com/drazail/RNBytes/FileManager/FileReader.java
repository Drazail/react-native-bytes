package com.drazail.RNBytes.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileReader {

    private File file;

    public FileReader(String path) throws IOException {
        this.file = new File(path);
        if (!this.file.exists()) throw new FileNotFoundException();
        if(this.file.isDirectory()) throw  new IOException("path points to a directory");
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
     *
     * @param startingIndex start reading from this byte (including the byte itself)
     * @param finalIndex last byte to be read (including the byte itself)
     * @return byte[] of all bytes read
     * @throws IOException IOException
     */
    public byte[] toByteArray(int startingIndex, int finalIndex) throws IOException {

        byte[] buffer = new byte[finalIndex - startingIndex + 1];

        RandomAccessFile RAF = new RandomAccessFile(this.file, "r");
        RAF.seek(startingIndex);
        RAF.read(buffer);
        RAF.close();

        return buffer;

    }

    public RandomAccessFile toRandomAccessFile(String mode) throws FileNotFoundException {
        RandomAccessFile RAF = new RandomAccessFile(this.file, mode);
        return RAF;
    }
}
