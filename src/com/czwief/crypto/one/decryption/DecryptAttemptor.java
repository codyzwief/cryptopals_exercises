package com.czwief.crypto.one.decryption;

import org.apache.commons.codec.DecoderException;

/**
 * Attempt to decrypt a given piece of ciphertext.
 * 
 * @author cody
 */
public interface DecryptAttemptor {

    /**
     * Attempt to decrypt the ciphertext with a given decryptor.
     * 
     * @param ciphertext
     * @param key
     * @return
     * @throws DecoderException 
     */
    public String attemptDecryption(final byte[] ciphertext, final Decryptor decryptor);
}
