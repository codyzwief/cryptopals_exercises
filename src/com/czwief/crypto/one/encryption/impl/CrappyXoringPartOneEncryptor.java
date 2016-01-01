package com.czwief.crypto.one.encryption.impl;

import com.czwief.crypto.one.encryption.Encryptor;
import javax.xml.bind.DatatypeConverter;

/**
 * Encrypts a string using a default method of simply XORing the plaintext
 * together with the key.
 * 
 * @author cody
 */
public class CrappyXoringPartOneEncryptor implements Encryptor {

    @Override
    public byte[] encrypt(String plaintext, String key, String iv) {
        final byte[] textHexBytes = plaintext.getBytes();
        final byte[] keyHexBytes = key.getBytes();
        final StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < textHexBytes.length; i++) {
            final byte singleByte = (byte) (textHexBytes[i] ^ keyHexBytes[i % keyHexBytes.length]);
            sb.append(DatatypeConverter.printHexBinary(new byte[]{singleByte}));
        }
        
        return sb.toString().toLowerCase().getBytes();
        
    }
    
}
