/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.decryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author cody
 */
public class AESDecryptor implements Decryptor {

    @Override
    public String decrypt(String ciphertext, String key) throws DecoderException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException|NoSuchPaddingException ex) {
            throw new IllegalArgumentException("BAD!!!", ex);
        }
        
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (InvalidKeyException ex) {
            throw new IllegalArgumentException("BAD!!!", ex);
        }
        try {
            byte[] decrypted = cipher.doFinal(ciphertext.getBytes());
            return new String(decrypted);
        } catch (IllegalBlockSizeException|BadPaddingException ex) {
            throw new IllegalArgumentException("BAD!!!" + ex.getMessage(), ex);
        }
    }

    @Override
    public String decrypt(byte[] ciphertext, String key) throws DecoderException {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException|NoSuchPaddingException ex) {
            throw new IllegalArgumentException("BAD!!!", ex);
        }
        
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (InvalidKeyException ex) {
            throw new IllegalArgumentException("BAD!!!", ex);
        }
        try {
            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted);
        } catch (IllegalBlockSizeException|BadPaddingException ex) {
            throw new IllegalArgumentException("BAD!!!" + ex.getMessage(), ex);
        }
    }
    
}
