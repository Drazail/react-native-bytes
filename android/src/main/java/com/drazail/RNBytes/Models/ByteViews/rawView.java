package com.drazail.RNBytes.Models.ByteViews;

import com.drazail.RNBytes.Models.ByteBuffer;

import java.util.UUID;

public class rawView {

    public Byte[] buffer;
    private String id;
    private int length;
    private int offset;
    private String encoding;
    private ByteBuffer byteBuffer;

    public rawView(String encoding, ByteBuffer buffer) {
        this.id = UUID.randomUUID().toString();
        this.buffer = buffer.getBuffer();
        this.encoding = encoding;
        this.length = buffer.getLength();
        this.offset = 0;
        this.byteBuffer = buffer;
    }

    public rawView(String encoding, ByteBuffer buffer, int length) {
        this.id = UUID.randomUUID().toString();
        this.buffer = buffer.getBuffer(length);
        this.encoding = encoding;
        this.length = length;
        this.offset = 0;
        this.byteBuffer = buffer;
    }

    public rawView(String encoding, ByteBuffer buffer, int srcOffset, int length) {
        this.id = UUID.randomUUID().toString();
        this.buffer = buffer.getBuffer(srcOffset, length);
        this.encoding = encoding;
        this.length = length;
        this.offset = srcOffset;
        this.byteBuffer = buffer;
    }

    /**
     * @return UUID associated with this View
     */
    public String getId() {
        return this.id;
    }

    /**
     * @return length of the view
     */
    public int getLength() {
        return this.length;
    }

    /**
     * @return offset of the view in reference to the original ByteBuffer
     */
    public int getOffset() {
        return this.offset;
    }

    /**
     * @return encoding of the view
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * @return buffer field of the object
     */
    public Byte[] getBuffer() {
        return this.buffer;
    }

    /**
     * @param buffer Byte[] to be set as the buffer field of the object
     */
    public void setBuffer(Byte[] buffer) {
        this.buffer = buffer;
    }

    /**
     * updates the underlying ByteBuffer associated with this object
     */
    public void updateByteBuffer() {
        this.byteBuffer.put(offset, buffer);
    }

}
