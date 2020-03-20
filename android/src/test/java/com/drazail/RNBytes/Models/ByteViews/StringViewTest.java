package com.drazail.RNBytes.Models.ByteViews;

import com.drazail.RNBytes.Models.ByteBuffer;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class StringViewTest {

    private ByteBuffer buffer;
    private StringView view1;
    private StringView view2;
    private StringView view3;


    StringViewTest() {
        this.buffer = new ByteBuffer(4);
        this.view1 = new StringView("ASCII", buffer);
        this.view2=new StringView("ASCII", buffer, 4);
        this.view3=new StringView("ASCII", buffer, 0,0);
    }

    @Test
    void TestStringView() {
        try {
            view1.setBufferFromString("1111", buffer);
            assertEquals(view1.getBufferAsString(),"1111", this.view1.getEncoding());
            assertArrayEquals(this.buffer.getBuffer(), view1.getBuffer(), this.view1.getEncoding());
        } catch (Exception e) {
            fail();
        }

        try {
            String str = this.view2.getBufferAsString();
            this.view3.setBufferFromString(str, buffer);
            assertArrayEquals(this.buffer.getBuffer(), view2.getBuffer(), this.view2.getEncoding());
        } catch (UnsupportedEncodingException e) {
            fail();
        }

        try {
            String str = this.view3.getBufferAsString();
            this.view3.setBufferFromString(str, buffer);
            assertArrayEquals(this.buffer.getBuffer(), view3.getBuffer(), this.view3.getEncoding());
        } catch (UnsupportedEncodingException e) {
            fail();
        }
    }


}