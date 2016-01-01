package com.czwief.crypto.two;

import com.czwief.crypto.one.ChallengeOneAnswers;
import com.czwief.crypto.one.decryption.Decryptor;
import com.czwief.crypto.one.encryption.GenericEncryptionDecryptionUtility;
import com.czwief.crypto.one.encryption.Encryptor;
import com.czwief.crypto.one.encryption.impl.HandrolledCBCEncryptor;
import com.czwief.crypto.two.padding.PKCS7Padder;
import com.czwief.crypto.utils.Base64Utils;
import com.czwief.crypto.utils.EncryptionMode;
import java.io.BufferedReader;
import java.io.FileReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * A simple test file for the crypto tests in http://cryptopals.com/sets/2/
 * 
 * @author cody
 */
public class ChallengeTwoTest {
            
    /**
     * 2.1 Implement PKCS#7 padding
     * 
     * A block cipher transforms a fixed-sized block (usually 8 or 16 bytes) of plaintext into ciphertext. 
     * But we almost never want to transform a single block; we encrypt irregularly-sized messages.
     * 
     * One way we account for irregularly-sized messages is by padding, creating a plaintext that is an even multiple of the blocksize. 
     * The most popular padding scheme is called PKCS#7.
     * 
     * So: pad any block to a specific block length, by appending the number of bytes of padding to the end of the block. For instance,
     * "YELLOW SUBMARINE"
     * ... padded to 20 bytes would be:
     * "YELLOW SUBMARINE\x04\x04\x04\x04"
     * 
     * @throws Exception 
     */
    @Test
    public void TwoPointOneTest() throws Exception {
        byte[] key = "YELLOW SUBMARINE".getBytes();
        
        Assert.assertEquals("YELLOW SUBMARINE\u0004\u0004\u0004\u0004",
                new String(PKCS7Padder.PKCS7Pad(key, 20)));
        
        Assert.assertEquals("YELLOW SUBMARINE\u0004\u0004\u0004\u0004",
                new String(PKCS7Padder.PKCS7Pad(key, 5)));
        
        Assert.assertEquals("YELLOW SUBMARINE\u0002\u0002",
                new String(PKCS7Padder.PKCS7Pad(key, 18)));
        
        Assert.assertEquals("YELLOW SUBMARINE",
                new String(PKCS7Padder.PKCS7Pad(key, 16)));
        
        Assert.assertEquals("YELLOW SUBMARINE",
                new String(PKCS7Padder.PKCS7Pad(key, 2)));
        
        Assert.assertEquals("YELLOW SUBMARINE",
                new String(PKCS7Padder.PKCS7Pad(key, 4)));
        
        Assert.assertEquals("YELLOW SUBMARINE\u0002\u0002",
                new String(PKCS7Padder.PKCS7Pad(key, 3)));
    }
    
    /**
     * 2.1 Implement CBC mode
     * 
     * CBC mode is a block cipher mode that allows us to encrypt irregularly-sized messages, 
     * despite the fact that a block cipher natively only transforms individual blocks.
     * 
     * In CBC mode, each ciphertext block is added to the next plaintext block before the next call to the cipher core.
     * 
     * The first plaintext block, which has no associated previous ciphertext block, 
     * is added to a "fake 0th ciphertext block" called the initialization vector, or IV.
     * 
     * Implement CBC mode by hand by taking the ECB function you wrote earlier, 
     * making it encrypt instead of decrypt (verify this by decrypting whatever you encrypt to test), 
     * and using your XOR function from the previous exercise to combine them.
     * 
     * The file here is intelligible (somewhat) when CBC decrypted against "YELLOW SUBMARINE" with an IV of all ASCII 0 (\x00\x00\x00 &c)
     * 
     * @throws Exception 
     */
    @Test
    public void TwoPointTwoTest() throws Exception {
        Decryptor cbcDecryptor = new GenericEncryptionDecryptionUtility("AES/CBC/PKCS5Padding", EncryptionMode.DECRYPT);
        String key = "YELLOW SUBMARINE";
        BufferedReader br = new BufferedReader(new FileReader("static-content/two/10.txt"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        
        Assert.assertEquals(ChallengeOneAnswers.ONE_POINT_SIX, 
                new String(cbcDecryptor.decrypt(Base64Utils.decode(sb.toString()), key)));
    }
    
    /**
     * While 2.2 test was simply "make sure you can decrypt this file,"
     * the exercise was to actually implement encryption with a random IV.
     * 
     * This test tests that functionality.
     * 
     * @throws Exception 
     */
    @Test
    public void TwoPointTwoTest_TestEncryptionAndDecryption() throws Exception {
        String plainText = ChallengeOneAnswers.ONE_POINT_SIX;
        String IV = new String(new byte[16]);
        Encryptor cbcEncryptor = new GenericEncryptionDecryptionUtility("AES/CBC/PKCS5Padding", EncryptionMode.ENCRYPT);
        Encryptor handrolledCbcEncryptor = new HandrolledCBCEncryptor();
        Decryptor cbcDecryptor = new GenericEncryptionDecryptionUtility("AES/CBC/PKCS5Padding", EncryptionMode.DECRYPT);
        String key = "YELLOW SUBMARINE";
        
        byte[] encrypted = cbcEncryptor.encrypt(plainText, key, IV);
        byte[] handrolledEncrypted = handrolledCbcEncryptor.encrypt(plainText, key, IV);
        
        System.err.println("=============================================");
        System.err.println("ENCRYPTED = " + new String(encrypted));
        System.err.println("=============================================");
        System.err.println("HANDROLLED= " + new String(handrolledEncrypted));
        System.err.println("=============================================");

        
        //Assert.assertEquals("Your handrolled encryption doesn't match the java implementation", 
        //        new String(encrypted), new String(handrolledEncrypted));
        
        byte[] decrypted = cbcDecryptor.decrypt(encrypted, key);
        byte[] decryptedFromHandrolled = cbcDecryptor.decrypt(handrolledEncrypted, key);
        
        System.err.println("HANDROLLED DECRYPTED = " + new String(decryptedFromHandrolled));
        
        Assert.assertEquals(plainText, new String(decrypted));
        Assert.assertEquals(plainText, new String(decryptedFromHandrolled));
    }
}