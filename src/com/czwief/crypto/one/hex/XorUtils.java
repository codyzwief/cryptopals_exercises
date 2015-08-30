/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.hex;

import com.czwief.crypto.one.scorer.StringScorer;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author cody
 */
public class XorUtils {
    
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    
    public static String attemptSingleDecryption(final String hexToDecode) throws DecoderException {
        int topScore = -1;
        String topScoreString = null;
        
        for (int i = 0; i < CHARACTERS.length(); i++) {
            String hexCharacter = Hex.encodeHexString(CHARACTERS.subSequence(i, i+1).toString().getBytes());
            String testString = HexUtils.xorHexStrings(hexToDecode, hexCharacter).getDisplayString();
           
            int score = StringScorer.scoreString(testString);
            if (score > topScore) {
                topScore = score;
                topScoreString = testString;
            }
        }
        
        return topScoreString;
    }
}
