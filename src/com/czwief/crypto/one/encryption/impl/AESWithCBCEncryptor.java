package com.czwief.crypto.one.encryption.impl;

import com.czwief.crypto.one.encryption.Encryptor;
import javax.xml.bind.DatatypeConverter;

/**
 * Encrypts a string using a random IV in CBC mode.
 * 
 * Instead of using Java's built-in implementations, we're doing this ourselves.
 * As exercise 2.10 says, "what's the point of doing this stuff if you're not
 * going to learn from it?"
 * 
 * Decryption notwithstanding...
 * 
 * @author cody
 */
public class AESWithCBCEncryptor implements Encryptor {

    @Override
    //TODO Implement...
    public String encrypt(String plaintext, String key, String iv) {
        final byte[] textHexBytes = plaintext.getBytes();
        final byte[] keyHexBytes = key.getBytes();
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < textHexBytes.length; i++) {
            final byte singleByte = (byte) (textHexBytes[i] ^ keyHexBytes[i % keyHexBytes.length]);
            sb.append(DatatypeConverter.printHexBinary(new byte[]{singleByte}));
        }
        
        return sb.toString().toLowerCase();
        
    }
    
}
