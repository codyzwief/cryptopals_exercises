package com.czwief.crypto.decryption.impl;

import com.czwief.crypto.decryption.DecryptAttemptor;
import com.czwief.crypto.decryption.Decryptor;
import com.czwief.crypto.one.strings.KeyLengthFinder;
import com.czwief.crypto.utils.MatrixUtils;
import com.czwief.crypto.utils.XorUtils;

/**
 * Attempt to decrypt a given piece of ciphertext.
 * 
 * @author cody
 */
public class DecryptAttemptorImpl implements DecryptAttemptor {
    
    private final Decryptor decryptor;
    
    public DecryptAttemptorImpl(final Decryptor decryptor) {
        this.decryptor = decryptor;
    }

    /**
     * Decrypt the ciphertext as specified in 1.6. This ignores the key.
     * 
     * http://cryptopals.com/sets/1/challenges/6/
     * 
     * @param ciphertext
     * @param key
     * @return
     * @throws DecoderException 
     */
    @Override
    public String attemptDecryption(final byte[] ciphertext) {
        
        //1. Since we're ignoring the key, attempt to figure out the key.
        //   Do this by determining a keysize
        Integer keySize = KeyLengthFinder.findKeySize(ciphertext);
        
        //2. Break the ciphertext into blocks of KEYSIZE each.        
        int tempIndex = 0;
        Integer temp = (int) Math.ceil((double) ciphertext.length / (double) keySize);
        byte[][] blocks = new byte[temp][keySize];
        for (int i = 0; i < ciphertext.length; i++) {
            if (tempIndex >= keySize) {
                tempIndex = 0;
            }
            blocks[i / keySize][tempIndex++] = ciphertext[i];
        }
        
        //3. Transpose the blocks.
        blocks = MatrixUtils.transpose(blocks);
        
        //4. Now find the key.
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.length; i++) {
            sb.append(XorUtils.attemptSingleDecryption(blocks[i], true));
        }
        
        //5. Decrypt it!
        return new String(decryptor.decrypt(ciphertext, sb.toString().getBytes()));
    }
}
