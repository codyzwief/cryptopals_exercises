package com.czwief.crypto.one.decryption.impl;

import com.czwief.crypto.one.decryption.Decryptor;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Decrypt a given ciphertext that was encrypted in AES with ECB mode.
 * 
 * @author cody
 */
public class AESWithECBDecryptor implements Decryptor {
    
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    @Override
    public String decrypt(final byte[] ciphertext, final String key) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException|NoSuchPaddingException ex) {
            throw new IllegalArgumentException("Unable to retrieve cipher for algorithm  " + ALGORITHM, ex);
        }
        
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (InvalidKeyException ex) {
            throw new IllegalArgumentException("Unable to initialize cipher with key  " + ReflectionToStringBuilder.toString(key) + " Exception is " + ex.getMessage());
        }
        try {
            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted);
        } catch (IllegalBlockSizeException|BadPaddingException ex) {
            throw new IllegalArgumentException("Unable to decrypt cipher text " + new String(ciphertext) + " with key " + key + " Exception is " + ex.getMessage());
        }
    }
    
}
