package com.drazail.RNBytes.Utils;

import com.drazail.RNBytes.FileManager.FileReader;
import com.drazail.RNBytes.Models.ByteBuffer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReferenceMapTest {

    @Test
    void addReader(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        ReferenceMap.addReader(reader.id, reader);

        assertEquals(ReferenceMap.getReader(reader.id), reader);

        reader.rm();
        reader.close();
    }

    @Test
    void removeReader(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        ReferenceMap.addReader(reader.id, reader);
        ReferenceMap.removeReader(reader.id);
        assertNull(ReferenceMap.getReader(reader.id));

        reader.rm();
        reader.close();
    }

    @Test
    void addByteArray() {
        ByteBuffer buffer = new ByteBuffer(1024);
        ReferenceMap.addByteArray(buffer.getId(), buffer);
        assertEquals(ReferenceMap.getByteArray(buffer.getId()), buffer);
    }

    @Test
    void removeByteArray() {
        ByteBuffer buffer = new ByteBuffer(1024);
        ReferenceMap.addByteArray(buffer.getId(), buffer);
        ReferenceMap.removeByteArray(buffer.getId());
        assertNull(ReferenceMap.getByteArray(buffer.getId()));
    }

    @Test
    void getReader(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        ReferenceMap.addReader(reader.id, reader);

        assertEquals(ReferenceMap.getReader(reader.id), reader);

        reader.rm();
        reader.close();
    }
}