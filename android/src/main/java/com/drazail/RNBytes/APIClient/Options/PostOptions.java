package com.drazail.RNBytes.APIClient.Options;

import java.util.Map;

public class PostOptions {

    private String url;
    private Map<String,String> headers;
    private Map<String,String> body;
    private BufferObject[] buffers;

    public String getUrl() {
        return this.url;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getBody() {
        return this.body;
    }

    public BufferObject[] getBuffers() {
        return this.buffers;
    }

    public PostOptions(String url, Map<String,String> headers, Map<String,String> body, BufferObject[] buffers){
        this.url = url;
        this.headers = headers;
        this.body = body;
        this.buffers = buffers;
    }
}
