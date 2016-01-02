package com.czwief.crypto.decryption;

/**
 *
 * @author cody
 */
public interface Decryptor {
    
    /**
     * Decrypt a piece of cipher text with the given key.
     * 
     * @param ciphertext
     * @param key TODO refactor everything to be just byte arrays
     * @return 
     */
    byte[] decrypt(byte[] ciphertext, byte[] key); 
}
