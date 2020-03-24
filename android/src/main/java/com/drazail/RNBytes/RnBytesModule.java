package com.drazail.RNBytes;

import android.util.Base64;

import com.drazail.RNBytes.FileManager.FileReader;
import com.drazail.RNBytes.FileManager.FileWriter;
import com.drazail.RNBytes.Models.ByteBuffer;
import com.drazail.RNBytes.Utils.ReferenceMap;
import com.drazail.RNBytes.Utils.ToRunnable;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.IOException;
import java.util.Objects;

public class RnBytesModule extends ReactContextBaseJavaModule {


    public RnBytesModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNBytes";
    }


    @ReactMethod
    public void createReader(String sourcePath, Promise callback ){

        try {
            FileReader reader = new FileReader(sourcePath);
            ReferenceMap.addReader(reader.id, reader);
            callback.resolve(reader.id);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }

    }

    @ReactMethod
    public void closeReader (String reader, String sourcePath, Promise callback ){
        try {
            ReferenceMap.removeReader(reader);
            callback.resolve(null);
        } catch (Exception e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void createBuffer(int size, Promise callback ){

            ByteBuffer buffer = new ByteBuffer(size);
            ReferenceMap.addByteArray(buffer.getId(), buffer);
            callback.resolve(buffer.getId());
    }

    @ReactMethod
    public void removeBuffer (String byteBuffer, Promise callback ){

        try {
            ReferenceMap.removeByteArray(byteBuffer);
            callback.resolve(null);
        } catch (Exception e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void getBufferAsBase64 (String byteBuffer, int off, int len, Promise callback ){
        try {
            if(off == -1 || len == -1){
                String string = Base64.encodeToString(ReferenceMap.byteArrayMap.get(byteBuffer).getBuffer(), Base64.DEFAULT);
                callback.resolve(string);
            }else{
                String string = Base64.encodeToString(ReferenceMap.byteArrayMap.get(byteBuffer).getBuffer(),off, len, Base64.DEFAULT);
                callback.resolve(string);
            }
        }catch (Exception e){
            callback.reject(e);
        }


    }

    @ReactMethod
    public void read(String reader, String byteBuffer, int off, int len, Promise callback){
        try {
            FileReader mReader = ReferenceMap.readerMap.get(reader);
            byte[] buffer = new byte[len];
            assert mReader != null;
            mReader.read(buffer,off, len );
            Objects.requireNonNull(ReferenceMap.byteArrayMap.get(byteBuffer)).setBuffer(buffer,0,0,len);
            callback.resolve(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void readFully(String reader, String byteBuffer, int off, int len, Promise callback){
        try {
            FileReader mReader = ReferenceMap.readerMap.get(reader);
            byte[] buffer = new byte[len];
            assert mReader != null;
            mReader.readFully(buffer,off, len );
            Objects.requireNonNull(ReferenceMap.byteArrayMap.get(byteBuffer)).setBuffer(buffer,0,0,len);
            callback.resolve(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }




    @ReactMethod
    public void readFromAndWriteTo(String sourcePath, String targetPath, boolean shouldOverWrite, boolean shoudlAppend, int FirstByteIndex, int finalByteIndex, Promise callback) {
        try {
            ToRunnable runnable = new ToRunnable(() -> {
                try {
                    FileReader reader = new FileReader(sourcePath);
                    byte[] buffer = reader.toByteArray(FirstByteIndex, finalByteIndex);
                    FileWriter writer = new FileWriter(targetPath, shouldOverWrite);
                    String path = writer.writeToFile(buffer, shoudlAppend);
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
    public void rm(String sourcePath, Promise callback){
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
