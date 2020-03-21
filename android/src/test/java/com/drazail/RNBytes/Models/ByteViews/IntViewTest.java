package com.drazail.RNBytes.Models.ByteViews;

import com.drazail.RNBytes.Models.ByteBuffer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

class IntViewTest {

    private ByteBuffer buffer;
    private IntView view1;
    private IntView view2;
    private IntView view3;


    IntViewTest() {
        this.buffer = new ByteBuffer(8);
        this.view1 = new IntView("ASCII", buffer);
        this.view2 = new IntView("ASCII", buffer, 8);
        this.view3 = new IntView("ASCII", buffer, 0, 8);
    }

    @Test
    void TestIntViewTest() {
        try {

            //test setBufferFromString and getBufferAsString
            view1.setBufferFromIntArray(new int[]{-1000, 1000});
            assertArrayEquals(view1.getBufferAsIntArray(), new int[]{-1000, 1000}, this.view1.getEncoding());

            //test setOriginalBuffer
            view1.setOriginalBuffer(this.buffer, 0, 0, this.buffer.getLength());
            assertArrayEquals(this.buffer.getBuffer(), view1.getBuffer(), this.view1.getEncoding());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e);
        }

        try {

            //test setBufferFromString and getBufferAsString
            view2.setBufferFromIntArray(new int[]{-1, 1});
            assertArrayEquals(view2.getBufferAsIntArray(), new int[]{-1, 1}, this.view2.getEncoding());

            //test setOriginalBuffer
            view2.setOriginalBuffer(this.buffer, 0, 0, this.buffer.getLength());
            assertArrayEquals(this.buffer.getBuffer(), view2.getBuffer(), this.view2.getEncoding());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e);
        }

        try {

            //test setBufferFromString and getBufferAsString
            view3.setBufferFromIntArray(new int[]{0, 10240});
            assertArrayEquals(view3.getBufferAsIntArray(), new int[]{0, 10240}, this.view3.getEncoding());

            //test setOriginalBuffer
            view3.setOriginalBuffer(this.buffer, 0, 0, this.buffer.getLength());
            assertArrayEquals(this.buffer.getBuffer(), view3.getBuffer(), this.view3.getEncoding());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e);
        }
    }

}