package com.czwief.crypto.one.encryption;

/**
 * Encrypt a given piece of plaintext with a given key.
 * 
 * @author cody
 */
public interface Encryptor {
    
    byte[] encrypt(String plaintext, String key, String iv);
    
}
