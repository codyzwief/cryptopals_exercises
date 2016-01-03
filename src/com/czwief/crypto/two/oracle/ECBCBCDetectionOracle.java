package com.czwief.crypto.two.oracle;

import com.czwief.crypto.decryption.DecryptAttemptor;
import com.czwief.crypto.decryption.impl.DecryptAttemptorImpl;
import com.czwief.crypto.encryption.Encryptor;
import com.czwief.crypto.encryption.GenericEncryptionDecryptionUtility;
import com.czwief.crypto.encryption.impl.RandomStringAppendingEncryptor;
import com.czwief.crypto.utils.EncryptionAlgorithmMode;
import com.czwief.crypto.utils.EncryptionMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author cody
 */
public class ECBCBCDetectionOracle {
    
    private final Encryptor randomStringAppendingEncryptor = new RandomStringAppendingEncryptor();
    private final DecryptAttemptor ecbDecryptor = 
            new DecryptAttemptorImpl(new GenericEncryptionDecryptionUtility("AES/ECB/PKCS5Padding", EncryptionMode.DECRYPT));
    
    public String decryptAppendedString() {
        final StringBuilder sb = new StringBuilder();
        final byte[] appendedStringEncrypted = randomStringAppendingEncryptor.encrypt(new byte[0], null, null);
        
        //1. Feed identical bytes of your-string to the function 1 at a time --- start with 1 byte ("A"), then "AA", then "AAA" and so on. 
        //Discover the block size of the cipher. You know it, but do this step anyway.
        final int blockSize = determineBlockSize();
        
        //2. Detect that the function is using ECB. You already know, but do this step anyways.
        //TODO Actually implement this correctly
        //Validate.isTrue(EncryptionAlgorithmMode.ECB == determineBlockCipherEncryptionMode(appendedStringEncrypted));
        
        //3. Knowing the block size, craft an input block that is exactly 1 byte short (for instance, if the block size is 8 bytes, make "AAAAAAA"). 
        //Think about what the oracle function is going to put in that last byte position.
        byte[] inputBlock = getInitialInputBlock(blockSize).getBytes();
        for (int x = 0; x < appendedStringEncrypted.length / blockSize; x++) {
            for (int i = 0; i < blockSize; i++) {
                final byte[] startingInput = Arrays.copyOfRange(inputBlock, i, inputBlock.length);
                final byte[] initialEncrypted = randomStringAppendingEncryptor.encrypt(startingInput, null, null);
                final byte[] hmm = ArrayUtils.addAll(startingInput, sb.toString().substring(blockSize * x).getBytes());

                //4. Make a dictionary of every possible last byte by feeding different strings to the oracle; 
                //for instance, "AAAAAAAA", "AAAAAAAB", "AAAAAAAC", remembering the first block of each invocation.
                final Map<String, Character> characterMap = new HashMap<>();
                for (int j = 0; j < 127; j++) {
                    final byte[] blockToUse = ArrayUtils.addAll(hmm, new byte[] {(byte) j});
                    byte[] encrypted = randomStringAppendingEncryptor.encrypt(blockToUse, null, null);
                    characterMap.put(new String(Arrays.copyOfRange(encrypted, 0, blockSize)), (char) j);
                }

                //5. Match the output of the one-byte-short input to one of the entries in your dictionary. 
                //You've now discovered the first byte of unknown-string.
                final byte[] key = Arrays.copyOfRange(initialEncrypted, blockSize * x, (blockSize * x) + blockSize);
                final String keyString = new String(key);
                sb.append(characterMap.get(keyString));
            }
            
            inputBlock = Arrays.copyOfRange(sb.toString().getBytes(), sb.length() - blockSize + 1, sb.length());
            //6. Repeat for the next byte.
        }
        
        return sb.toString();
    }
    
    private String getInitialInputBlock(final int blockSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blockSize - 1; i++) {
            sb.append("A");
        }
        return sb.toString();
    }
    
    public int determineBlockSize() {
        final int firstTest = randomStringAppendingEncryptor.encrypt(new byte[0], null, null).length;
        int willEventuallyChange = firstTest;
        final StringBuilder sb = new StringBuilder();
        while (willEventuallyChange == firstTest) {
            final byte[] plainText = sb.append("A").toString().getBytes();
            willEventuallyChange = randomStringAppendingEncryptor.encrypt(plainText, null, null).length;
        }
        return willEventuallyChange - firstTest;
    }
    
    //TODO FIX
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
