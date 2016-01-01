package com.czwief.crypto.two.padding;

import java.util.Arrays;

/**
 *
 * @author cody
 */
public class PKCS7Padder {
    
    public static byte[] PKCS7Pad(byte[] data, int blockSize) {
        final int numberOfBytesToPad = (blockSize - (data.length % blockSize)) % blockSize;
        final byte[] newCopy = Arrays.copyOf(data, data.length + numberOfBytesToPad);
        Arrays.fill(newCopy, data.length, newCopy.length, (byte) numberOfBytesToPad);
        return newCopy;
    }
    
}
