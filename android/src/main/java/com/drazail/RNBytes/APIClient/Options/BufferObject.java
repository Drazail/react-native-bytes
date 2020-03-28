package com.drazail.RNBytes.APIClient.Options;

import okhttp3.MediaType;

public class BufferObject {
    private String name;
    private String fileName;
    private byte[] buffer;
    private MediaType mediaType;

    public BufferObject(String name, String fileName, byte[] buffer, MediaType mediaType) {
        this.name = name;
        this.fileName = fileName;

        this.buffer = buffer;
        this.mediaType = mediaType;

    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getName() {
        return this.name;
    }

    public String getFileName() {
        return this.fileName;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }
}
