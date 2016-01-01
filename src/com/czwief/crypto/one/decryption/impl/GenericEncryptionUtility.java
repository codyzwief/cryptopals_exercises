package com.czwief.crypto.one.decryption.impl;

import com.czwief.crypto.one.decryption.Decryptor;
import com.czwief.crypto.one.encryption.Encryptor;
import com.czwief.crypto.utils.EncryptionMode;
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
public class GenericEncryptionUtility implements Decryptor, Encryptor {
    
    private final Cipher cipher;
    private final String keyMode;
    private final EncryptionMode encryptionMode;
    
    public GenericEncryptionUtility(final String algorithm, final EncryptionMode encryptionMode) {
        try {
            this.cipher = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException|NoSuchPaddingException ex) {
            throw new IllegalArgumentException("Unable to retrieve cipher for algorithm  " + algorithm 
                    + ". Exception is: " + ex.getMessage());
        }
        this.keyMode = algorithm.split("/")[0];
        this.encryptionMode = encryptionMode;
    }
    
    @Override
    public byte[] decrypt(final byte[] ciphertext, final String key) {
                System.out.println("decrypting size = " + ciphertext.length);

        return encryptOrDecrypt(ciphertext, key, new byte[16]);
    }

    public byte[] encryptOrDecrypt(final byte[] ciphertext, final String key, final byte[] iv) {
        
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), keyMode);
        try {
            if (isCBC()) {
                System.out.println("It's CBC!");
                cipher.init(encryptionMode.getEncryptionMode(), secretKey, new IvParameterSpec(iv));
            } else {
                cipher.init(encryptionMode.getEncryptionMode(), secretKey);
            }
        } catch (InvalidKeyException|InvalidAlgorithmParameterException ex) {
            System.out.println(ex);
            throw new IllegalArgumentException("Unable to initialize cipher with key  " + ReflectionToStringBuilder.toString(key) 
                    + " Exception is " + ex.getMessage());
        }
        try {
            return cipher.doFinal(ciphertext);
        } catch (IllegalBlockSizeException|BadPaddingException ex) {
            System.out.println("size = " + ciphertext.length);
            System.out.println(ex);
            ex.printStackTrace();
            throw new IllegalArgumentException("Unable to encrypt/decrypt cipher text " //+ new String(ciphertext) 
                    + " with key " + key + " Exception is " + ex.getMessage());
        }
    }
    
    private boolean isCBC() {
        return cipher.getAlgorithm().contains("CBC");
    }

    @Override
    public byte[] encrypt(String plaintext, String key, String iv) {
        System.out.println("encrypting size = " + plaintext.getBytes().length + " text = " + plaintext);
        return encryptOrDecrypt(plaintext.getBytes(), key, iv.getBytes());
    }
    
}
