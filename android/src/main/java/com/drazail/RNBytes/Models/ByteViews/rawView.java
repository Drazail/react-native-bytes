package com.drazail.RNBytes.Models.ByteViews;

import com.drazail.RNBytes.Models.ByteBuffer;

import java.util.UUID;

public class rawView {

    public byte[] buffer;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    private int length;
    private int offset;
    private String encoding;

    public rawView(String encoding, ByteBuffer buffer) {
        this.id = UUID.randomUUID().toString();
        this.buffer = buffer.getBuffer();
        this.encoding = encoding;
        this.length = buffer.getLength();
        this.offset = 0;
    }

    public rawView(String encoding, ByteBuffer buffer, int length) {
        this.id = UUID.randomUUID().toString();
        this.buffer = buffer.getBuffer(length);
        this.encoding = encoding;
        this.length = length;
        this.offset = 0;
    }

    public rawView(String encoding, ByteBuffer buffer, int srcOffset, int length) {
        this.id = UUID.randomUUID().toString();
        this.buffer = buffer.getBuffer(srcOffset, length);
        this.encoding = encoding;
        this.length = length;
        this.offset = srcOffset;

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
    public byte[] getBuffer() {
        return this.buffer;
    }

    /**
     * @param buffer Byte[] to be set as the buffer field of the object
     */
    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    /**
     *
     * @param byteBuffer ByteBuffer to be manipulated
     * @param srcOffset first index to be copied from this,buffer to byteBuffer
     * @param desOffset starting copying index of byteBuffer
     * @param length number of bytes to be copied over to byteBuffer
     */
    public void setOriginalBuffer(ByteBuffer byteBuffer, int srcOffset, int desOffset, int length){
        byteBuffer.setBuffer(this.buffer,srcOffset,desOffset,length);
    }


}
