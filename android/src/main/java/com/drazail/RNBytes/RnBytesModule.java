package com.drazail.RNBytes;

import com.drazail.RNBytes.FileManager.FileReader;
import com.drazail.RNBytes.FileManager.FileWriter;
import com.drazail.RNBytes.Utils.ToRunnable;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class RnBytesModule extends ReactContextBaseJavaModule {


    public RnBytesModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNBytes";
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
