package com.drazail.RNBytes;

import android.util.Base64;

import com.drazail.RNBytes.APIClient.APIClient;
import com.drazail.RNBytes.APIClient.Options.BufferObject;
import com.drazail.RNBytes.APIClient.Options.PostOptions;
import com.drazail.RNBytes.FileManager.FileReader;
import com.drazail.RNBytes.FileManager.FileWriter;
import com.drazail.RNBytes.Models.ByteBuffer;
import com.drazail.RNBytes.Models.ByteViews.IntView;
import com.drazail.RNBytes.Models.ByteViews.StringView;
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

    /**
     *
     * @param sourcePath URI of the file to be read
     * @param callback ReactNative Promise interface
     */

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

    /**
     *
     * @param reader reference to the reader
     * @param callback ReactNative Promise interface
     */

    @ReactMethod
    public void closeReader(String reader, Promise callback) {
        try {
            FileReader fileReader = ReferenceMap.getReader(reader);
            fileReader.close();
            callback.resolve(reader);
        } catch (Exception e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    /**
     *
     * @param size buffer size in bytes
     * @param callback ReactNative Promise interface
     */
    @ReactMethod
    public void createBuffer(int size, Promise callback) {

        ByteBuffer buffer = new ByteBuffer(size);
        callback.resolve(buffer.getId());
    }

    /**
     *
     * @param byteBuffer reference to the byteBuffer
     * @param callback ReactNative Promise interface
     */
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

    /**
     *
     * @param reader reference to the reader
     * @param byteBuffer reference to the buffer
     * @param off buffer offset
     *       * <p> The first byte read is stored into element <code>b[0]</code>, the
     *      * next one into <code>b[1]</code>, and so on. The number of bytes read is,
     *      * at most, equal to the length of <code>buffer</code>. Let <i>k</i> be the
     *      * number of bytes actually read; these bytes will be stored in elements
     *      * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
     *      * leaving elements <code>b[</code><i>k</i><code>]</code> through
     *      * <code>b[b.length-1]</code> unaffected.
     * @param len length to be read into the buffer
     * @param callback ReactNative Promise interface
     */
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

    /**
     * Reads exactly {@code len} bytes from reader source into the buffer,
     * This method reads
     * repeatedly from the file until the requested number of bytes are
     * read. This method blocks until the requested number of bytes are
     * read, the end of the stream is detected, or an exception is thrown.
     * @param reader reference to the reader
     * @param byteBuffer reference to the buffer
     * @param off buffer offset
     *       * <p> The first byte read is stored into element <code>b[0]</code>, the
     *      * next one into <code>b[1]</code>, and so on. The number of bytes read is,
     *      * at most, equal to the length of <code>buffer</code>. Let <i>k</i> be the
     *      * number of bytes actually read; these bytes will be stored in elements
     *      * <code>b[0]</code> through <code>b[</code><i>k</i><code>-1]</code>,
     *      * leaving elements <code>b[</code><i>k</i><code>]</code> through
     *      * <code>b[b.length-1]</code> unaffected.
     * @param len length to be read into the buffer
     * @param callback ReactNative Promise interface
     */
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

    /**
     *
     * @param byteBuffer reference to the buffer
     * @param off buffer offset
     * @param len length to be read
     * @param callback ReactNative Promise interface
     */

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


    /**
     *
     * @param view reference to a view
     * @param buffer reference to the buffer
     * @param srcOffset view offset
     * @param desOffset buffer offset
     * @param length number of bytes to be written into the buffer
     * @param callback ReactNative Promise interface
     */
    @ReactMethod
    public void updateBuffer(String view, String buffer, int srcOffset, int desOffset, int length, Promise callback) {
        rawView mView = ReferenceMap.getView(view);
        ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
        mView.setOriginalBuffer(mBuffer, srcOffset, desOffset, length);
        callback.resolve(buffer);
    }


    /**
     *
     * @param buffer reference to a buffer
     * @param length length of the view
     * @param sourceOffset buffer offset
     * @param callback ReactNative Promise interface
     */
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


    /**
     *
     * @param intView reference to an intView
     * @param callback ReactNative Promise interface
     */
    @ReactMethod

    public void getArrayFromIntView(String intView, Promise callback) {
        IntView mIntView = (IntView) ReferenceMap.getView(intView);
        WritableArray writableArray = new WritableNativeArray();
        int[] IntArray = mIntView.getBufferAsIntArray();

        for (int a : IntArray) {
            writableArray.pushInt(a);
        }
        callback.resolve(writableArray);
    }


    /**
     *
     * @param intView reference to an intView
     * @param readableArray int[]
     * @param callback ReactNative Promise interface
     */
    @ReactMethod

    public void setIntViewAray(String intView, ReadableArray readableArray, Promise callback) {
        IntView mIntView = (IntView) ReferenceMap.getView(intView);

        int[] array = new int[readableArray.size()];

        for (int i = 0; i < readableArray.size(); i++) {
            array[i] = readableArray.getInt(i);
        }
        mIntView.setBufferFromIntArray(array);
        callback.resolve(intView);
    }


    /**
     *
     * @param buffer reference to a buffer
     * @param length length of the view
     * @param sourceOffset buffer offset
     * @param encoding  one of Standard charsets -
     *                 *Canonical Name for java.io API NOT the java.nio API ie:
     *                 *use "ASCII" not "US-ASCII"
     * @param callback ReactNative Promise interface
     */
    @ReactMethod

    public void createStringView(String buffer, int length, int sourceOffset, String encoding, Promise callback) {
        if (length == -1) {
            ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
            StringView stringView = new StringView(encoding, mBuffer);
            callback.resolve(stringView.getId());
        } else if (sourceOffset == 0) {
            ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
            StringView stringView = new StringView(encoding, mBuffer, length);
            callback.resolve(stringView.getId());
        } else {
            ByteBuffer mBuffer = ReferenceMap.getByteArray(buffer);
            StringView stringView = new StringView(encoding, mBuffer, sourceOffset, length);
            callback.resolve(stringView.getId());
        }
    }

    /**
     *
     * @param stringView reference to a StringView
     * @param callback ReactNative Promise interface
     */

    @ReactMethod

    public void getStringFromStringView(String stringView, Promise callback) {
        try{
            StringView mStringView = (StringView) ReferenceMap.getView(stringView);
            String string = mStringView.getBufferAsString();
            callback.resolve(string);
        }catch (Exception e){
            e.printStackTrace();
            callback.reject(e);
        }

    }


    /**
     *
     * @param stringView reference to a StringView
     * @param string string to be written into the view
     * @param callback ReactNative Promise interface
     */
    @ReactMethod

    public void setStringViewValue(String stringView, String string, Promise callback) {
        StringView mStringView = (StringView) ReferenceMap.getView(stringView);
        mStringView.setBufferFromString(string);
        callback.resolve(mStringView.getId());
    }


    /**
     *
     * @param buffer reference to buffer
     * @param targetPath URI of the target file
     * @param shouldOverWrite should overWrite if the file exists
     * @param shouldAppend should append the file if exists
     * @param srcOffset buffer offset
     * @param length number of bytes to be read from the buffer
     * @param callback ReactNative Promise interface
     */

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
     * @param callback ReactNative Promise interface
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

    /**
     *
     * @param sourcePath URI of the source file
     * @param targetPath URI of the target file
     * @param shouldOverWrite should overWrite if the file exists
     * @param shouldAppend should append the file if exists
     * @param FirstByteIndex the index of the first byte read from the source
     * @param finalByteIndex final byte index to be read from the source
     * @param callback ReactNative Promise interface
     */
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


    /**
     *
     * @param sourcePath URI of the source file
     * @param callback ReactNative Promise interface
     */
    @ReactMethod
    public void rm(String sourcePath, Promise callback) {
        try {
            ToRunnable runnable = new ToRunnable(() -> {
                try {
                    FileReader reader = new FileReader(sourcePath);
                    reader.rm();
                    callback.resolve(null);
                } catch (Exception e) {
                    callback.reject(e);
                }
            });
            runnable.run();
        } catch (Exception e) {
            callback.reject(e);
        }
    }

    /**
     *
     * @param path URI of the file
     * @param callback ReactNative Promise interface
     */
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
