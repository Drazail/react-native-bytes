package com.drazail.RNBytes.Models.ByteViews;

import android.util.Log;

import com.drazail.RNBytes.Models.ByteBuffer;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class StringView extends rawView {
    public StringView(String encoding, ByteBuffer buffer) {
        super(encoding, buffer);
    }

    public StringView(String encoding, ByteBuffer buffer, int length) {
        super(encoding, buffer, length);
    }

    public StringView(String encoding, ByteBuffer buffer, int srcOffset, int length) {
        super(encoding, buffer, srcOffset, length);
    }


    /**
     * packing each set of 4 bytes from the view to an int, big endian
     *
     * @return intArray
     */
    public String getBufferAsString() throws UnsupportedEncodingException {
        byte[] bytes = new byte[this.buffer.length];
        int i = 0;
        for (Byte b : this.buffer) {
            bytes[i++] = b;
        }

        return new String(bytes, this.getEncoding());
    }

    /**
     * @param str
     */
    public void setBufferFromString(String str, ByteBuffer buffer) {
        byte[] byts = str.getBytes(Charset.forName(this.getEncoding()));
        Byte[] bytes = new Byte[byts.length];
        int i = 0;
        for (byte b : byts) bytes[i++] = b;
        buffer.setBuffer(bytes);
        this.buffer = bytes;
    }
}
