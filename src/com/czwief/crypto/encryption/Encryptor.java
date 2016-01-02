package com.czwief.crypto.encryption;

/**
 * Encrypt a given piece of plaintext with a given key.
 * 
 * @author cody
 */
public interface Encryptor {
    
    /**
     * Encrypt a piece of data with a given key and IV.
     * 
     * @param plaintext
     * @param key
     * @param iv
     * @return 
     */
    byte[] encrypt(byte[] plaintext, byte[] key, byte[] iv);
    
}
