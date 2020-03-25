package com.drazail.RNBytes.Utils;

import com.drazail.RNBytes.FileManager.FileReader;
import com.drazail.RNBytes.Models.ByteBuffer;
import com.drazail.RNBytes.Models.ByteViews.rawView;

import java.util.HashMap;
import java.util.Map;

public class ReferenceMap {
    private static Map<String, FileReader> readerMap = new HashMap<>();
    private static Map<String, ByteBuffer> byteArrayMap = new HashMap<>();
    private static Map<String, rawView> ViewMap = new HashMap<>();

    public static void addReader(String name, FileReader reader) {
        readerMap.put(name, reader);
    }

    public static void removeReader(String name) {
        readerMap.remove(name);
    }

    public static void addByteArray(String name, ByteBuffer byteArray) {
        byteArrayMap.put(name, byteArray);
    }

    public static void addView(String name, rawView view) {
        ViewMap.put(name, view);
    }

    public static void removeByteArray(String name) {
        byteArrayMap.remove(name);
    }

    public static FileReader getReader(String id) {
        return readerMap.get(id);
    }

    public static ByteBuffer getByteArray(String id) {
        return byteArrayMap.get(id);
    }

    public static rawView getView(String id) {
        return ViewMap.get(id);
    }

}