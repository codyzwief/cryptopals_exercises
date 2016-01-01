package com.czwief.crypto.one.decryption.impl;

import com.czwief.crypto.one.decryption.Decryptor;

/**
 * A simple decryption implementation that simply XORs the cipher text
 * against the bytes of the key.
 * 
 * @author cody
 */
public class CrappyXoringPartOneDecryptor implements Decryptor {

    @Override
    public byte[] decrypt(byte[] ciphertext, String key) {
        final byte[] keyAsBytes = key.getBytes();
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < ciphertext.length; i++) {
            byte singleByte = (byte) (ciphertext[i] ^ keyAsBytes[i % keyAsBytes.length]);
            sb.append(new String(new byte[]{singleByte}));
        }
        
        return sb.toString().getBytes();
    }
}
