package com.drazail.RNBytes.Models;

import com.drazail.RNBytes.Utils.ReferenceMap;

import java.util.Arrays;
import java.util.UUID;

import static java.lang.System.arraycopy;

public class ByteBuffer {

    public byte[] buffer;
    private String id;
    private int len;

    /**
     * constructor
     *
     * @param length
     */
    public ByteBuffer(int length) {
        this.id = UUID.randomUUID().toString();
        this.buffer = new byte[length];
        Arrays.fill(this.buffer, (byte) 1);
        this.len = length;
        ReferenceMap.addByteArray(this.id, this);
    }

    /**
     * @return length of the buffer
     */
    public int getLength() {
        return this.len;
    }

    /**
     * @return a new copy of ByteBuffer
     */
    public ByteBuffer transfer() {
        ByteBuffer newBuffer = new ByteBuffer(this.buffer.length);
        newBuffer.put(this.buffer);
        return newBuffer;
    }

    /**
     * @return buffer field of the class
     */
    public byte[] getBuffer() {
        return this.buffer;
    }

    /**
     * @param src     the source array.
     * @param srcPos  starting position in the source array.
     * @param destPos starting position in the destination data.
     * @param length  the number of array elements to be copied.
     */
    public void setBuffer(byte[] src, int srcPos, int destPos, int length) {
        arraycopy(src, srcPos, this.buffer, destPos, length);
    }

    /**
     * @param length return buffer size
     * @return first length elements of buffer field of the class
     */
    public byte[] getBuffer(int length) {
        byte[] chuck = new byte[length];
        arraycopy(this.buffer, 0, chuck, 0, length);
        return chuck;
    }

    /**
     * @param srcOffset starting index of the buffer field
     * @param length    return buffer size
     * @return elements srcOffset up to and including srcOffset+length element buffer field of the class
     */
    public byte[] getBuffer(int srcOffset, int length) {
        byte[] chuck = new byte[length];
        arraycopy(this.buffer, srcOffset, chuck, 0, length);
        return chuck;
    }

    /**
     * @return the UUID associated with this ByteBuffer
     */
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * overwrites the existing elements of the buffer
     * will Throw ArrayIndexOutOfBoundsException if the input is longer than the buffer
     *
     * @param byteArray the values to be written into the buffer
     */
    public void put(byte[] byteArray) {
        if (byteArray.length <= this.len) {
            arraycopy(byteArray, 0, this.buffer, 0, byteArray.length);
        } else {
            throw new ArrayIndexOutOfBoundsException("input length is larger than the src length ");
        }
    }

    /**
     * overwrites the existing elements of the buffer starting from srcOffset
     * will Throw ArrayIndexOutOfBoundsException if the input is larger than allocated space in buffer
     *
     * @param srcOffset index of the first element in buffer to be written onto
     * @param byteArray he values to be written into the buffer
     */
    public void put(int srcOffset, byte[] byteArray) {
        for (int i = 0; i < byteArray.length; i++) {
            if (srcOffset + i < this.len) {
                this.buffer[srcOffset + i] = byteArray[i];
            } else {
                throw new ArrayIndexOutOfBoundsException("input is larger than allocated space in src ");
            }

        }
    }
}
