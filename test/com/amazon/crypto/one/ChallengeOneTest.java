/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazon.crypto.one;

import com.czwief.crypto.one.decryption.DecryptAttemptor;
import com.czwief.crypto.one.distance.StringDistance;
import com.czwief.crypto.one.encryption.DefaultEncryptor;
import com.czwief.crypto.one.encryption.Encryptor;
import com.czwief.crypto.one.hex.Base64Utils;
import com.czwief.crypto.one.hex.XorUtils;
import com.czwief.crypto.one.scorer.StringScorer;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.commons.codec.binary.Hex;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * A simple test file for the crypto tests in http://cryptopals.com/sets/1/
 * 
 * @author cody
 */
public class ChallengeOneTest {
    
    private final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * 1.1 Convert hex to base64
     * 
     * The string:
     * 49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d
     * 
     * Should produce:
     * SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t
     * 
     * So go ahead and make that happen. You'll need to use this code for the rest of the exercises.
     * 
     * @throws Exception 
     */
    @Test
    public void OnePointOneTest() throws Exception {
        String hex =
                "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
        
        Assert.assertEquals(
                "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t",
                Base64Utils.toBase64(hex));
    }
    
    /**
     * 1.2 Fixed XOR
     * 
     * Write a function that takes two equal-length buffers and produces their XOR combination.
     * 
     * If your function works properly, then when you feed it the string:
     * 1c0111001f010100061a024b53535009181c
     * 
     * after hex decoding, and when XOR'd against: 686974207468652062756c6c277320657965
     * 
     * should produce: 746865206b696420646f6e277420706c6179
     * 
     * @throws Exception 
     */
    @Test
    public void OnePointTwoTest() throws Exception {
        String hex = "1c0111001f010100061a024b53535009181c";
        String xorWith = "686974207468652062756c6c277320657965";

        Assert.assertEquals(
                "746865206b696420646f6e277420706c6179",
                XorUtils.xorHexStrings(hex, xorWith).getHexString());
    }
    
    
    /**
     * 1.3. Single-byte XOR cipher
     * 
     * The hex encoded string: 1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736
     * has been XOR'd against a single character. Find the key, decrypt the message.
     * 
     * You can do this by hand. But don't: write code to do it for you.
     * 
     * How? Devise some method for "scoring" a piece of English plaintext.
     * Character frequency is a good metric. Evaluate each output and choose the one with the best score.
     * 
     * @throws Exception 
     */
    @Test
    public void OnePointThreeTest() throws Exception {
        String hex = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
        String topScoreString = XorUtils.attemptSingleDecryption(hex, false);
        Assert.assertEquals("Cooking MC's like a pound of bacon", topScoreString);
    }
    
    /**
     * 1.4 Detect single-character XOR
     * 
     * One of the 60-character strings in this file has been encrypted by single-character XOR.
     * Find it.
     * (Your code from #3 should help.)
     * 
     * @throws Exception
     */
    @Test
    public void OnePointFourTest() throws Exception {
        
        BufferedReader br = new BufferedReader(new FileReader("static-content/one/4.txt"));
        String line;
        int topScore = -1;
        String topScoreString = null;
        while ((line = br.readLine()) != null) {
            for (int i = 0; i < CHARACTERS.length(); i++) {
                String hexCharacter = Hex.encodeHexString(CHARACTERS.subSequence(i, i+1).toString().getBytes());
                String testString = XorUtils.xorHexStrings(line, hexCharacter).getDisplayString();
           
                int score = StringScorer.scoreString(testString);
                if (score > topScore) {
                    topScore = score;
                    topScoreString = testString;
                }
            }
        }
        Assert.assertEquals("Now that the party is jumping\n", topScoreString);
    }
    
    
    /**
     * 1.5 Implement repeating-key XOR
     * 
     * Here is the opening stanza of an important work of the English language:
     *  Burning 'em, if you ain't quick and nimble
     *  I go crazy when I hear a cymbal
     * 
     * Encrypt it, under the key "ICE", using repeating-key XOR.
     * In repeating-key XOR, you'll sequentially apply each byte of the key; 
     * the first byte of plaintext will be XOR'd against I, the next C, the next E, then I again for the 4th byte, and so on.
     * 
     * It should come out to:
     * 0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272
     * a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f
     * 
     * Encrypt a bunch of stuff using your repeating-key XOR function. Encrypt your mail. 
     * Encrypt your password file. Your .sig file. Get a feel for it. I promise, we aren't wasting your time with this.
     * 
     * @throws Exception
     */
    @Test
    public void OnePointFiveTest() throws Exception {
        final String KEY = "ICE";
        final String TEXT = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal";
        Encryptor encryptor = new DefaultEncryptor();
        final String result = encryptor.encrypt(TEXT, KEY);
        
        final String expectedResult = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272"
                 + "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f";
        Assert.assertEquals(expectedResult, result);
    }
    
    @Test
    public void StringDistanceTest() {
        String one = "this is a test";
        String two = "wokka wokka!!!";
        
        Assert.assertEquals(37, StringDistance.determineDistanceBetween(one, two));
    }
    
    /**
     * 1.6 There's a file here. It's been base64'd after being encrypted with repeating-key XOR.
     * Decrypt it.
     * 
     * http://cryptopals.com/sets/1/challenges/6/
     * 
     * @throws Exception
     */
    @Test
    public void OnePointSixTest() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("static-content/one/6.txt"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        
        System.out.println("BASE STRING = " + sb.toString());
        String result = new DecryptAttemptor().decrypt(sb.toString(), null);
        System.out.println("RESULT = " + result);
    }
}