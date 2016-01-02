package com.czwief.crypto.two.oracle;

import com.czwief.crypto.decryption.DecryptAttemptor;
import com.czwief.crypto.decryption.impl.DecryptAttemptorImpl;
import com.czwief.crypto.encryption.GenericEncryptionDecryptionUtility;
import com.czwief.crypto.utils.EncryptionAlgorithmMode;
import com.czwief.crypto.utils.EncryptionMode;

/**
 *
 * @author cody
 */
public class ECBCBCDetectionOracle {
    
    private DecryptAttemptor ecbDecryptor = new DecryptAttemptorImpl(new GenericEncryptionDecryptionUtility("AES/ECB/PKCS5Padding", EncryptionMode.DECRYPT));
    
    public EncryptionAlgorithmMode determineBlockCipherEncryptionMode(final byte[] ciphertext) {
        final byte[] attemptedDecryption = ecbDecryptor.attemptDecryption(ciphertext).getBytes();
        boolean doesItStartWithZeroes = true;
        for (int i = 0; i < 5; i++) {
            doesItStartWithZeroes = doesItStartWithZeroes || attemptedDecryption[i] == (byte)0;
        }
        
        if (doesItStartWithZeroes) {
            return EncryptionAlgorithmMode.ECB;
        }
        return EncryptionAlgorithmMode.CBC;
    }
    
}
