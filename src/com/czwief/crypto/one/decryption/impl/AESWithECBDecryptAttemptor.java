package com.czwief.crypto.one.decryption.impl;

import com.czwief.crypto.one.decryption.DecryptAttemptor;
import com.czwief.crypto.one.decryption.Decryptor;
import com.czwief.crypto.utils.MatrixUtils;
import com.czwief.crypto.utils.XorUtils;
import org.apache.commons.codec.DecoderException;

/**
 * Since this is just finishing set one
 * 
 * @author cody
 */
public class AESWithECBDecryptAttemptor implements DecryptAttemptor {

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
    public String attemptDecryption(final byte[] ciphertext, final Decryptor decryptor) {
        
        //1. Since we're ignoring the key, attempt to figure out the key.
        //   Do this by determining a keysize
        Integer keySize = 16;
        
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
        return decryptor.decrypt(ciphertext, sb.toString());
    }
}
