package com.drazail.RNBytes.APIClient;


import com.drazail.RNBytes.APIClient.Options.BufferObject;
import com.drazail.RNBytes.APIClient.Options.PostOptions;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {

    // one instance, reuse
    private final OkHttpClient httpClient = new OkHttpClient();

    public Response post(PostOptions options) throws IOException {

        //extracting options
        String url = options.getUrl();
        Map<String, String> headers = options.getHeaders();
        Map<String, String> body = options.getBody();
        BufferObject[] buffers = options.getBuffers();

        //building request
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        MultipartBody.Builder RequestBodyBuilder = new MultipartBody.Builder();
        RequestBodyBuilder.setType(MultipartBody.FORM);

        // adding bodies
        Iterator<Map.Entry<String, String>> bodyIterator = body.entrySet().iterator();
        while (
                bodyIterator.hasNext()
        ) {
            Map.Entry<String, String> bodyPair = bodyIterator.next();
            RequestBodyBuilder.addFormDataPart(bodyPair.getKey(), bodyPair.getValue());
            // avoids a ConcurrentModificationException
            bodyIterator.remove();
        }

        // adding buffers
        for (
                BufferObject buffer : buffers
        ) {
            String name = buffer.getName();
            String fileName = buffer.getFileName();
            byte[] bytes = buffer.getBuffer();
            MediaType mediaType = buffer.getMediaType();

            RequestBodyBuilder.addFormDataPart(name, fileName, RequestBody.create(mediaType, bytes));
        }

        RequestBody form = RequestBodyBuilder.build();

        requestBuilder.post(form);

        //adding headers
        Iterator<Map.Entry<String, String>> headerIterator = headers.entrySet().iterator();

        while (
                headerIterator.hasNext()
        ) {
            Map.Entry<String, String> headerPair = bodyIterator.next();
            requestBuilder.addHeader(headerPair.getKey(), headerPair.getValue());
            // avoids a ConcurrentModificationException
            headerIterator.remove();
        }

        return httpClient.newCall(requestBuilder.build()).execute();
    }

}

