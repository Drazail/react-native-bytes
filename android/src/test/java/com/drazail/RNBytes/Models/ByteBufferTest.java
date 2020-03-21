package com.drazail.RNBytes.Models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteBufferTest {

    ByteBuffer buffer1 = new ByteBuffer(4);

    @Test
    void getLength() {
        assertEquals(buffer1.getLength(), 4);
    }

    @Test
    void transfer() {
        buffer1.setBuffer("helo".getBytes(), 0, 0, 4);
        byte[] transferedBuffer = buffer1.transfer().getBuffer();
        assertArrayEquals(transferedBuffer, buffer1.buffer);
    }

    @Test
    void setBuffer() {
        byte[] newBuffer = new byte[4];
        Arrays.fill(newBuffer, (byte) -1);
        buffer1.setBuffer(newBuffer, 0, 0, 4);
        assertArrayEquals(newBuffer, buffer1.getBuffer());
    }

    @Test
    void getBuffer() {
        buffer1.setBuffer("helo".getBytes(), 0, 0, 4);
        byte[] thisBuffer = buffer1.getBuffer(4);
        assertArrayEquals(thisBuffer, buffer1.buffer);
    }

    @Test
    void getBuffer1() {
        buffer1.setBuffer("helo".getBytes(), 0, 0, 4);
        byte[] thisBuffer = buffer1.getBuffer(2);
        assertArrayEquals(thisBuffer, new byte[]{buffer1.buffer[0], buffer1.buffer[1]});
    }

    @Test
    void getBuffer2() {
        buffer1.setBuffer("helo".getBytes(), 0, 0, 4);
        byte[] thisBuffer = buffer1.getBuffer(2, 2);
        assertArrayEquals(thisBuffer, new byte[]{buffer1.buffer[2], buffer1.buffer[3]});
    }

    @Test
    void setId() {
        buffer1.setId("testId");
        assertEquals(buffer1.getId(), "testId");
    }

    @Test
    void getId() {
        buffer1.setId("testId2");
        assertEquals(buffer1.getId(), "testId2");
    }

    @Test
    void put() {
        buffer1.setBuffer("helo".getBytes(), 0, 0, 4);
        buffer1.put("yo".getBytes());
        byte[] thisBuffer = buffer1.getBuffer();
        assertArrayEquals(thisBuffer, "yolo".getBytes());
    }

    @Test
    void put1() {
        buffer1.setBuffer("helo".getBytes(), 0, 0, 4);
        buffer1.put(2, "ya".getBytes());
        byte[] thisBuffer = buffer1.getBuffer();
        assertArrayEquals(thisBuffer, "heya".getBytes());
    }
}