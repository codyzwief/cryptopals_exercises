package com.czwief.crypto.one.decryption;

import org.apache.commons.codec.DecoderException;

/**
 *
 * @author cody
 */
public interface Decryptor {
    
    String decrypt(String ciphertext, String key) throws DecoderException;
    
    String decrypt(byte[] ciphertext, String key) throws DecoderException;
    
}
