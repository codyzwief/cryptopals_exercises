package com.czwief.crypto.one.encryption.impl;

import com.czwief.crypto.one.encryption.Encryptor;
import com.czwief.crypto.one.encryption.GenericEncryptionDecryptionUtility;
import com.czwief.crypto.two.padding.PKCS7Padder;
import com.czwief.crypto.utils.EncryptionMode;
import com.czwief.crypto.utils.MatrixUtils;
import com.czwief.crypto.utils.XorUtils;
import org.apache.commons.lang3.Validate;

/**
 * Handrolling manual CBC mode 
 * 
 * @author cody
 */
public class HandrolledCBCEncryptor implements Encryptor {
    
    private static final Encryptor ecbEncryptor = 
            new GenericEncryptionDecryptionUtility("AES/ECB/PKCS5Padding", EncryptionMode.ENCRYPT);

    @Override
    public byte[] encrypt(final String plaintext, final String key, final String iv) {
        final byte[] keyBytes = key.getBytes();
        final byte[] ivBytes = iv.getBytes();
        final int blockSize = keyBytes.length;
        Validate.isTrue(keyBytes.length == ivBytes.length, "Key and IV need to be same length.");
        final byte[] textBytes = PKCS7Padder.PKCS7Pad(plaintext.getBytes(), blockSize);
        
        final byte[] retval = new byte[textBytes.length];
        final byte[][] blockedTextBytes = MatrixUtils.chopIntoBlocks(textBytes, blockSize);
        
        byte[] tempXorMask = ivBytes;
        
        for (int i = 0; i < blockedTextBytes.length; i++) {
            byte[] toEncrypt = XorUtils.xorBytes(blockedTextBytes[i], tempXorMask, false);
            byte[] encrypted = ecbEncryptor.encrypt(new String(toEncrypt), key, null);
            tempXorMask = encrypted;
            System.arraycopy(encrypted, 0, retval, i * blockSize, blockSize);
        }
        
        return retval;
        
    }
    
}
