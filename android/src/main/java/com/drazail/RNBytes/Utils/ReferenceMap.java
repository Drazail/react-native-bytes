package com.drazail.RNBytes.Utils;

import com.drazail.RNBytes.FileManager.FileReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.RandomAccess;

public class ReferenceMap {
    public static Map<String, FileReader> readerMap = new HashMap<String, FileReader>();
    public static Map<String, byte[]> byteArrayMap = new HashMap<String, byte[]>();

    public static void addReader(String name, FileReader reader){
        readerMap.put(name, reader);
    }

    public static void removeReader(String name) throws IOException {
        FileReader reader = readerMap.get(name);
        reader.close();
        readerMap.remove(name);
    }

    public static void addByteArray(String name, byte[] byteArray){
        byteArrayMap.put(name, byteArray);
    }

    public static void removeByteArray(String name) throws IOException {
        byteArrayMap.remove(name);
    }

}