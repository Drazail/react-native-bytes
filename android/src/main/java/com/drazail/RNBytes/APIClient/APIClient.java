package com.drazail.RNBytes.APIClient;


import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
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

    public Response post(String url, ReadableMap headers, ReadableMap body) throws IOException {
        FormBody.Builder formBody = new FormBody.Builder();
        Request.Builder requestBuilder = new Request.Builder();

        requestBuilder.url(url);

        for (
                ReadableMapKeySetIterator keyIterator = body.keySetIterator();
                keyIterator.hasNextKey();
        ) {
            String key = keyIterator.nextKey();
            formBody.add(key, Objects.requireNonNull(headers.getString(key)));
        }

        FormBody form = formBody.build();

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

