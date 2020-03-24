package com.drazail.RNBytes.FileManager;

import com.drazail.RNBytes.Models.ByteBuffer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FileWriterTest {

    @Test
    void writeToFile(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("testFile");
        Path testWriterFile = tempDir.resolve("testWriterFile");
        ByteBuffer buffer = new ByteBuffer(1024);
        byte[] emptyBuffer = new byte[1024];
        Files.write(testFile, buffer.getBuffer());
        FileReader reader = new FileReader(testFile.toAbsolutePath().toString());
        reader.read(emptyBuffer, 0, 1024);
        FileWriter writer = new FileWriter(testWriterFile.toAbsolutePath().toString(), false);
        writer.writeToFile(emptyBuffer, false);
        assertAll(
                () -> assertArrayEquals(emptyBuffer, Files.readAllBytes(testWriterFile))
        );
        reader.rm();
        reader.close();
    }

}