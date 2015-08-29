/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazon.crypto.one;

import com.czwief.crypto.one.HexUtils;
import com.czwief.crypto.one.scorer.StringScorer;
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
                HexUtils.hexToBase64(hex));
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
                HexUtils.xorHexStrings(hex, xorWith));
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
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        
        int topScore = -1;
        String topScoreString = null;
        
        for (int i = 0; i < characters.length(); i++) {
            String hexCharacter = Hex.encodeHexString(characters.subSequence(i, i+1).toString().getBytes());
            String testString = HexUtils.xorHexStringsDifferentLength(hex, hexCharacter);
           
            int score = StringScorer.scoreString(testString);
            if (score > topScore) {
                topScore = score;
                topScoreString = testString;
            }
        }
        Assert.assertEquals("Cooking MC's like", topScoreString);
    }

}