package com.drazail.RNBytes.Models.ByteViews;

import com.drazail.RNBytes.Models.ByteBuffer;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class rawViewTest {

    private ByteBuffer buffer;
    private rawView view1;
    private rawView view2;
    private rawView view3;


    rawViewTest() {
        this.buffer = new ByteBuffer(8);
        this.view1 = new rawView("ASCII", buffer);
        this.view2 = new rawView("ASCII", buffer, 8);
        this.view3 = new rawView("ASCII", buffer, 0, 8);
    }

    @Test
    void getId() {
        this.view1.setId("testId");
        assertEquals(this.view1.getId(), "testId");
    }

    @Test
    void setId() {
        this.view1.setId("testId2");
        assertEquals(this.view1.getId(), "testId2");
    }

    @Test
    void getLength() {
        assertEquals(this.view1.getLength(), 8);
        assertEquals(this.view2.getLength(), 8);
        assertEquals(this.view3.getLength(), 8);
    }

    @Test
    void getOffset() {
        assertEquals(this.view3.getOffset(), 0);
    }

    @Test
    void getEncoding() {
        assertEquals(this.view1.getEncoding(), "ASCII");
    }

    @Test
    void getBuffer() {
        assertEquals(view1.getBuffer(), this.buffer.getBuffer());
    }

    @Test
    void setBuffer() {
        String str = "hello wo";
        byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
        view1.setBuffer(bytes);
        assertArrayEquals(view1.getBuffer(),bytes);
    }

    @Test
    void setOriginalBuffer() {

        String str = "hello wo";
        byte[] bytes = str.getBytes(Charset.forName("UTF-8"));
        view1.setBuffer(bytes);
        view1.setOriginalBuffer(this.buffer, 0, 0, this.buffer.getLength());
        assertArrayEquals(this.buffer.getBuffer(), view1.getBuffer(), this.view1.getEncoding());
        assertArrayEquals(bytes, this.buffer.getBuffer());
    }

}