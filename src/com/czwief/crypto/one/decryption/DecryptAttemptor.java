package com.czwief.crypto.one.decryption;

import org.apache.commons.codec.DecoderException;

/**
 * Attempt to decrypt a given piece of ciphertext.
 * 
 * @author cody
 */
public interface DecryptAttemptor {

    /**
     * Attempt to decrypt the ciphertext.
     * 
     * @param ciphertext
     * @param key
     * @return
     * @throws DecoderException 
     */
    public String attemptDecryption(final byte[] ciphertext);
}
