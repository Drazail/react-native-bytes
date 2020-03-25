package com.drazail.RNBytes.APIClient;


import com.drazail.RNBytes.Utils.ReferenceMap;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {

    // one instance, reuse
    private final OkHttpClient httpClient = new OkHttpClient();


    public Response get(String url, ReadableMap headers) throws IOException {

        Request.Builder request = new Request.Builder();
        request.url(url);

        for (
                ReadableMapKeySetIterator keyIterator = headers.keySetIterator();
                keyIterator.hasNextKey();
        ) {
            String key = keyIterator.nextKey();
            request.addHeader(key, Objects.requireNonNull(headers.getString(key)));
        }

        return httpClient.newCall(request.build()).execute();
    }

    public Response post(String url, ReadableMap headers, ReadableMap body, ReadableMap buffers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        MultipartBody.Builder RequestBodyBuilder = new MultipartBody.Builder();
        RequestBodyBuilder.setType(MultipartBody.FORM);
        for (
                ReadableMapKeySetIterator keyIterator = body.keySetIterator();
                keyIterator.hasNextKey();
        ) {
            String key = keyIterator.nextKey();
            RequestBodyBuilder.addFormDataPart(key, Objects.requireNonNull(body.getString(key)));
        }

        for (
                ReadableMapKeySetIterator keyIterator = buffers.keySetIterator();
                keyIterator.hasNextKey();
        ) {
            String key = keyIterator.nextKey();
            ReadableMap bufferMap = buffers.getMap(key);
            assert bufferMap != null;
            byte[] buffer = ReferenceMap.getByteArray(bufferMap.getString("buffer")).getBuffer(bufferMap.getInt("offset"),bufferMap.getInt("length"));
            String fileName = bufferMap.getString("fileName");
            String name =  bufferMap.getString("name");
            RequestBodyBuilder.addFormDataPart(name, fileName, RequestBody.create(null, buffer));
        }

        RequestBody  form = RequestBodyBuilder.build();

        requestBuilder.post(form);

        for (
                ReadableMapKeySetIterator keyIterator = headers.keySetIterator();
                keyIterator.hasNextKey();
        ) {
            String key = keyIterator.nextKey();
            requestBuilder.addHeader(key, Objects.requireNonNull(headers.getString(key)));
        }

        return httpClient.newCall(requestBuilder.build()).execute();
    }

}

