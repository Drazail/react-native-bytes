package com.drazail.RNBytes;

import android.util.Base64;

import com.drazail.RNBytes.APIClient.APIClient;
import com.drazail.RNBytes.APIClient.Options.BufferObject;
import com.drazail.RNBytes.APIClient.Options.PostOptions;
import com.drazail.RNBytes.FileManager.FileReader;
import com.drazail.RNBytes.FileManager.FileWriter;
import com.drazail.RNBytes.Models.ByteBuffer;
import com.drazail.RNBytes.Models.ByteViews.IntView;
import com.drazail.RNBytes.Models.ByteViews.rawView;
import com.drazail.RNBytes.Utils.ReferenceMap;
import com.drazail.RNBytes.Utils.ToRunnable;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.Response;

public class RnBytesModule extends ReactContextBaseJavaModule {


    public RnBytesModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNBytes";
    }


    @ReactMethod
    public void createReader(String sourcePath, Promise callback) {

        try {
            FileReader reader = new FileReader(sourcePath);
            callback.resolve(reader.id);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }

    }

    @ReactMethod
    public void closeReader(String reader, String sourcePath, Promise callback) {
        try {
            FileReader fileReader = ReferenceMap.getReader(reader);
            fileReader.close();
            callback.resolve(reader);
        } catch (Exception e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void createBuffer(int size, Promise callback) {

        ByteBuffer buffer = new ByteBuffer(size);
        callback.resolve(buffer.getId());
    }

    @ReactMethod
    public void removeBuffer(String byteBuffer, Promise callback) {

        try {
            ReferenceMap.removeByteArray(byteBuffer);
            callback.resolve(null);
        } catch (Exception e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void readToBuffer(String reader, String byteBuffer, int off, int len, Promise callback) {
        try {
            FileReader mReader = ReferenceMap.getReader(reader);
            byte[] buffer = new byte[len];
            assert mReader != null;
            mReader.read(buffer, off, len);
            Objects.requireNonNull(ReferenceMap.getByteArray(byteBuffer)).setBuffer(buffer, 0, 0, len);
            callback.resolve(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void readFullyToBuffer(String reader, String byteBuffer, int off, int len, Promise callback) {
        try {
            FileReader mReader = ReferenceMap.getReader(reader);
            byte[] buffer = new byte[len];
            assert mReader != null;
            mReader.readFully(buffer, off, len);
            Objects.requireNonNull(ReferenceMap.getByteArray(byteBuffer)).setBuffer(buffer, 0, 0, len);
            callback.resolve(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void getBufferAsBase64(String byteBuffer, int off, int len, Promise callback) {
        try {
            if (off == -1 || len == -1) {
                String string = Base64.encodeToString(ReferenceMap.getByteArray(byteBuffer).getBuffer(), Base64.DEFAULT);
                callback.resolve(string);
            } else {
                String string = Base64.encodeToString(ReferenceMap.getByteArray(byteBuffer).getBuffer(), off, len, Base64.DEFAULT);
                callback.resolve(string);
            }
        } catch (Exception e) {
            callback.reject(e);
        }


    }

    @ReactMethod

    public void createIntView(String buffer, int length, int sourceOffset, Promise callback) {
        if (length == -1) {
            ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
            IntView intView = new IntView("ASCII", mBuffer);
            callback.resolve(intView.getId());
        } else if (sourceOffset == 0) {
            ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
            IntView intView = new IntView("ASCII", mBuffer, length);
            callback.resolve(intView.getId());
        } else {
            ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
            IntView intView = new IntView("ASCII", mBuffer, sourceOffset, length);
            callback.resolve(intView.getId());
        }
    }

    @ReactMethod

    public void getViewIntArray(String intView, Promise callback) {
        IntView mIntView = (IntView) ReferenceMap.getView(intView);
        WritableArray writableArray = new WritableNativeArray();
        int[] IntArray = mIntView.getBufferAsIntArray();

        for (int a : IntArray) {
            writableArray.pushInt(a);
        }
        callback.resolve(writableArray);
    }

    @ReactMethod

    public void setViewIntArray(String intView, ReadableArray readableArray, Promise callback) {
        IntView mIntView = (IntView) ReferenceMap.getView(intView);

        int[] array = new int[readableArray.size()];

        for (int i = 0; i < readableArray.size(); i++) {
            array[i] = readableArray.getInt(i);
        }
        mIntView.setBufferFromIntArray(array);
        callback.resolve(intView);
    }

    @ReactMethod
    public void setOriginalBuffer(String view, String buffer, int srcOffset, int desOffset, int length, Promise callback) {
        rawView mView = ReferenceMap.getView(view);
        ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
        mView.setOriginalBuffer(mBuffer, srcOffset, desOffset, length);
        callback.resolve(buffer);
    }


    @ReactMethod

    public void writeBufferToFile(String buffer, String targetPath, boolean shouldOverWrite, boolean shouldAppend, int srcOffset, int length, Promise callback) {
        try {
            ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
            byte[] byteArray = mBuffer.getBuffer(srcOffset, length);
            FileWriter writer = new FileWriter(targetPath, shouldOverWrite);
            String path = writer.writeToFile(byteArray, shouldAppend);
            callback.resolve(path);
        } catch (Exception e) {
            callback.reject(e);
        }

    }

    /**
     *
     * @param url url
     * @param buffers ReadableArray of Maps {name: string, fileName: string, mediaType:string, buffer:string}
     * @param headers ReadableArray of Maps {key: string, value:string}
     * @param bodies ReadableArray of Maps {key: string, value:string}
     * @param callback promise
     */
    @ReactMethod
    public void postBuffers(
            String url,
            ReadableArray buffers,
            ReadableArray headers,
            ReadableArray bodies,
            Promise callback) {

        ToRunnable runnable = new ToRunnable(() -> {
            try {
                BufferObject[] bufferObjects = new BufferObject[buffers.size()];
                for (int i = 0; i < buffers.size(); i++) {
                    ByteBuffer mBuffer = ReferenceMap.getByteArray(Objects.requireNonNull(buffers.getMap(i)).getString("buffer"));
                    byte[] byteArray = mBuffer.getBuffer();
                    MediaType mType = MediaType.parse(Objects.requireNonNull(Objects.requireNonNull(buffers.getMap(i)).getString("mediaType")));
                    BufferObject bufferOptions = new BufferObject(buffers.getMap(i).getString("name"), buffers.getMap(i).getString("fileName"), byteArray, mType);
                    bufferObjects[i] = bufferOptions;
                }

                Map<String, String> headersMap = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    headersMap.put(Objects.requireNonNull(headers.getMap(i).getString("key")), Objects.requireNonNull(headers.getMap(i).getString("value")));
                }

                Map<String, String> bodiesMap = new HashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    bodiesMap.put(Objects.requireNonNull(bodies.getMap(i).getString("key")), Objects.requireNonNull(bodies.getMap(i).getString("value")));
                }
                PostOptions postOptions = new PostOptions(url, headersMap, bodiesMap, bufferObjects);

                APIClient client = new APIClient();

                Response res = client.post(postOptions);

                callback.resolve(res.toString());
            } catch (Exception e) {
                callback.resolve(e);
            }

        });

        runnable.run();
    }

    @ReactMethod
    public void readFromAndWriteTo(String sourcePath, String targetPath, boolean shouldOverWrite, boolean shouldAppend, int FirstByteIndex, int finalByteIndex, Promise callback) {
        try {
            ToRunnable runnable = new ToRunnable(() -> {
                try {
                    FileReader reader = new FileReader(sourcePath);
                    byte[] buffer = reader.toByteArray(FirstByteIndex, finalByteIndex);
                    FileWriter writer = new FileWriter(targetPath, shouldOverWrite);
                    String path = writer.writeToFile(buffer, shouldAppend);
                    callback.resolve(path);
                } catch (Exception e) {
                    callback.reject(e);
                }
            });
            runnable.run();
        } catch (Exception e) {
            callback.reject(e);
        }

    }


    @ReactMethod
    public void rm(String sourcePath, Promise callback) {
        try {
            ToRunnable runnable = new ToRunnable(() -> {
                try {
                    FileReader reader = new FileReader(sourcePath);
                    reader.rm();
                } catch (Exception e) {
                    callback.reject(e);
                }
            });
            runnable.run();
        } catch (Exception e) {
            callback.reject(e);
        }
    }

    @ReactMethod
    public void getFileLength(String path, Promise callback) {
        try {
            ToRunnable runnable = new ToRunnable(() -> {
                try {
                    FileReader reader = new FileReader(path);
                    int length = (int) reader.getFileSize();
                    callback.resolve(length);
                } catch (Exception e) {
                    callback.reject(e);
                }
            });
            runnable.run();
        } catch (Exception e) {
            callback.reject(e);
        }
    }
}
