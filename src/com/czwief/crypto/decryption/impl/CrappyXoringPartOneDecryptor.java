package com.czwief.crypto.decryption.impl;

import com.czwief.crypto.decryption.Decryptor;

/**
 * A simple decryption implementation that simply XORs the cipher text
 * against the bytes of the key.
 * 
 * @author cody
 */
public class CrappyXoringPartOneDecryptor implements Decryptor {

    @Override
    public byte[] decrypt(byte[] ciphertext, byte[] key) {
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < ciphertext.length; i++) {
            byte singleByte = (byte) (ciphertext[i] ^ key[i % key.length]);
            sb.append(new String(new byte[]{singleByte}));
        }
        
        return sb.toString().getBytes();
    }
}
