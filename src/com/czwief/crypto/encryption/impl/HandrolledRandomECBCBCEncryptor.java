package com.czwief.crypto.encryption.impl;

import com.czwief.crypto.encryption.Encryptor;
import com.czwief.crypto.encryption.GenericEncryptionDecryptionUtility;
import com.czwief.crypto.padding.PKCS7Padder;
import com.czwief.crypto.utils.EncryptionMode;
import com.czwief.crypto.utils.KeyGenerationUtils;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Encrypts a string, ignoring the key and IV passed in. This generates its own
 * and will encrypt in ECB half the time and CBC the other half.
 * 
 * @author cody
 */
public class HandrolledRandomECBCBCEncryptor implements Encryptor {
    
    String algorithm = null;

    @Override
    public byte[] encrypt(byte[] plaintext, byte[] keyThatIsIgnored, byte[] ivThatIsIgnored) {
        final byte[] keyToUse = KeyGenerationUtils.generateAESKey(16);
        final byte[] ivToUse = KeyGenerationUtils.generateAESKey(16);
        final Encryptor actualEncryptor = new GenericEncryptionDecryptionUtility(determineEncryptionMode(), EncryptionMode.ENCRYPT);
        
        //Under the hood, have the function append 5-10 bytes (count chosen randomly) 
        //before the plaintext and 5-10 bytes after the plaintext.
        final byte[] plaintextToUse = PKCS7Padder.PKCS7Pad(getCorrectPlaintext(plaintext), 16);
        
        return actualEncryptor.encrypt(plaintextToUse, keyToUse, ivToUse);
    }
    
    //Under the hood, have the function append 5-10 bytes (count chosen randomly) 
    //before the plaintext and 5-10 bytes after the plaintext.
    private byte[] getCorrectPlaintext(final byte[] plaintext) {
        final Random rand = new Random();
        final byte[] prefix = new byte[rand.nextInt(6) + 5];
        final byte[] postfix = new byte[rand.nextInt(6) + 5];
        
        byte[] prefixAdded = ArrayUtils.addAll(prefix, plaintext);
        return ArrayUtils.addAll(prefixAdded, postfix);
        
    }
    
    private String determineEncryptionMode() {
        if (algorithm != null) {
            return algorithm;
        }
        final Random rand = new Random();
        algorithm = String.format("AES/%s/PKCS5Padding", rand.nextBoolean() ? "CBC" : "ECB");
        return algorithm;
    }
}
