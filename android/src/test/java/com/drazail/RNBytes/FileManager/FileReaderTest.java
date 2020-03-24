package com.drazail.RNBytes.FileManager;

import com.drazail.RNBytes.Models.ByteBuffer;
import com.drazail.RNBytes.Utils.ReferenceMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileReaderTest {

    @Test
    void getFileSize(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        assertAll(
                () -> assertTrue(Files.exists(testFile)),
                () -> assertTrue(Files.size(testFile) == 1024),
                () -> assertTrue(reader.getFileSize() == 1024)
        );
        reader.rm();
        reader.close();
    }

    @Test
    void getAbsolutePath(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        assertAll(
                () -> assertTrue(Files.exists(testFile)),
                () -> assertEquals(testFile.toString(), reader.getAbsolutePath())
        );
        reader.rm();
        reader.close();
    }

    @Test
    void toByteArray(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        assertAll(
                () -> assertTrue(Files.exists(testFile)),
                () -> assertArrayEquals(reader.toByteArray(0, 1023), buffer.getBuffer()),
                () -> assertArrayEquals(reader.toByteArray(0, 99), buffer.getBuffer(100)),
                () -> assertArrayEquals(reader.toByteArray(100, 199), buffer.getBuffer(100, 100))
        );
        reader.rm();
        reader.close();
    }

    @Test
    void rm(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        reader.close();
        reader.rm();
        assertAll(
                () -> assertFalse(Files.exists(testFile))
        );
    }

    @Test
    void read(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        byte[] emptyBuffer = new byte[1024];
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        reader.read(emptyBuffer, 0, 1024);
        assertAll(
                () -> assertArrayEquals(emptyBuffer, buffer.getBuffer())
        );
        reader.rm();
        reader.close();
    }

    @Test
    void readFully(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        byte[] emptyBuffer = new byte[1024];
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        reader.readFully(emptyBuffer, 0, 1024);
        assertAll(
                () -> assertArrayEquals(emptyBuffer, buffer.getBuffer())
        );
        reader.rm();
        reader.close();
    }

    @Test
    void close(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        String readerId = reader.id;
        reader.rm();
        reader.close();
        assertAll(
                () -> assertNull(ReferenceMap.getReader(readerId))
        );

    }
}