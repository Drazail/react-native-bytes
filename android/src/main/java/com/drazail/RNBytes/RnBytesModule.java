package com.drazail.RNBytes;

import android.util.Base64;

import com.drazail.RNBytes.FileManager.FileReader;
import com.drazail.RNBytes.FileManager.FileWriter;
import com.drazail.RNBytes.Utils.ReferenceMap;
import com.drazail.RNBytes.Utils.ToRunnable;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.IOException;

public class RnBytesModule extends ReactContextBaseJavaModule {


    public RnBytesModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNBytes";
    }


    @ReactMethod
    public void createReader(String name, String sourcePath, Promise callback ){

        try {
            FileReader reader = new FileReader(sourcePath);
            ReferenceMap.addReader(name, reader);
            callback.resolve(name);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }

    }

    @ReactMethod
    public void closeReader (String name, String sourcePath, Promise callback ){
        try {
            ReferenceMap.removeReader(name);
            callback.resolve(null);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void createBuffer(String name, int size, Promise callback ){

            byte[] buffer = new byte[size];
            ReferenceMap.addByteArray(name, buffer);
            callback.resolve(name);
    }

    @ReactMethod
    public void removeBuffer (String name, Promise callback ){

        try {
            ReferenceMap.removeByteArray(name);
            callback.resolve(null);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void getBufferAsBase64 (String name, int off, int len, Promise callback ){
        try {
            if(off == -1 || len == -1){
                String string = Base64.encodeToString(ReferenceMap.byteArrayMap.get(name), Base64.DEFAULT);
                callback.resolve(string);
            }else{
                String string = Base64.encodeToString(ReferenceMap.byteArrayMap.get(name),off, len, Base64.DEFAULT);
                callback.resolve(string);
            }
        }catch (Exception e){
            callback.reject(e);
        }


    }

    @ReactMethod
    public void read(String readerName, String bufferName, int off, int len, Promise callback){
        FileReader reader = ReferenceMap.readerMap.get(readerName);
        byte[] buffer = ReferenceMap.byteArrayMap.get(bufferName);
        try {
            reader.read(buffer,off, len );
            callback.resolve(bufferName);
        } catch (IOException e) {
            e.printStackTrace();
            callback.reject(e);
        }
    }

    @ReactMethod
    public void readFully(String readerName, String bufferName, int off, int len, Promise callback){
        FileReader reader = ReferenceMap.readerMap.get(readerName);
        byte[] buffer = ReferenceMap.byteArrayMap.get(bufferName);
        try {
            reader.readFully(buffer,off, len );
            callback.resolve(bufferName);
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
