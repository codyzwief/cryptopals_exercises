package com.czwief.crypto.one.decryption.impl;

import com.czwief.crypto.one.decryption.Decryptor;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Decrypt a given ciphertext that can be encrypted in any mode with the given algorithm.
 * 
 * Algorithm specs can be found here: http://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html
 * 
 * @author cody
 */
public class GenericDecryptor implements Decryptor {
    
    private final Cipher cipher;
    private final String keyMode;
    
    public GenericDecryptor(final String algorithm) {
        try {
            this.cipher = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException|NoSuchPaddingException ex) {
            throw new IllegalArgumentException("Unable to retrieve cipher for algorithm  " + algorithm 
                    + ". Exception is: " + ex.getMessage());
        }
        this.keyMode = algorithm.split("/")[0];
    }

    @Override
    public String decrypt(final byte[] ciphertext, final String key) {
        
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), keyMode);
        try {
            if (isCBC()) {
                cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }
        } catch (InvalidKeyException|InvalidAlgorithmParameterException ex) {
            throw new IllegalArgumentException("Unable to initialize cipher with key  " + ReflectionToStringBuilder.toString(key) 
                    + " Exception is " + ex.getMessage());
        }
        try {
            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted);
        } catch (IllegalBlockSizeException|BadPaddingException ex) {
            throw new IllegalArgumentException("Unable to decrypt cipher text " + new String(ciphertext) 
                    + " with key " + key + " Exception is " + ex.getMessage());
        }
    }
    
    private boolean isCBC() {
        return cipher.getAlgorithm().contains("CBC");
    }
    
}
