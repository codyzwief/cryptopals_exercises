/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.utils;

import javax.crypto.Cipher;

/**
 *
 * @author cody
 */
public enum EncryptionMode {
    ENCRYPT(Cipher.ENCRYPT_MODE),
    DECRYPT(Cipher.DECRYPT_MODE);
    
    private int encryptionMode;
    
    private EncryptionMode(int encryptionMode) {
        this.encryptionMode = encryptionMode;
    }
    
    public int getEncryptionMode() {
        return encryptionMode;
    }
}
