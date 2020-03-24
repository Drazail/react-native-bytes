package com.drazail.RNBytes.Utils;

import com.drazail.RNBytes.FileManager.FileReader;
import com.drazail.RNBytes.Models.ByteBuffer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.RandomAccess;

public class ReferenceMap {
    public static Map<String, FileReader> readerMap = new HashMap<>();
    public static Map<String, ByteBuffer> byteArrayMap = new HashMap<>();

    public static void addReader(String name, FileReader reader){
        readerMap.put(name, reader);
    }

    public static void removeReader(String name) {
        readerMap.remove(name);
    }

    public static void addByteArray(String name, ByteBuffer byteArray){
        byteArrayMap.put(name, byteArray);
    }

    public static void removeByteArray(String name) {
        byteArrayMap.remove(name);
    }

    public static FileReader getReader(String id){
        return readerMap.get(id);
    }

    public static ByteBuffer getByteArray(String id){
        return byteArrayMap.get(id);
    }

}