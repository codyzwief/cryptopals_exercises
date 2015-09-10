package com.czwief.crypto.utils;

import com.czwief.crypto.one.scorer.StringScorer;

/**
 *
 * @author cody
 */
public class XorUtils {
        
    public static String attemptSingleDecryption(final byte[] hexToDecode, final boolean shouldReturnKeyInstead) {
        int topScore = -1;
        String topScoreString = null;
        int bestKey = Integer.MIN_VALUE;
        
        for (int i = 0; i < 128; i++) {
            byte[] hexCharacter = Character.toString((char) i).getBytes();
            String testString = new String(xorBytes(hexToDecode, hexCharacter));
           
            int score = StringScorer.scoreString(testString);
            if (score > topScore) {
                topScore = score;
                topScoreString = testString;
                bestKey = i;
            }
        }
        if (shouldReturnKeyInstead) {
            return Character.toString((char) bestKey);
        }
        return topScoreString;
    }
    
    public static byte[] xorBytes(final byte[] first, final byte[] second) {
        return xorBytes(first, second, true);
    }
    
    
    private static byte[] xorBytes(byte[] first, byte[] second, boolean shouldWrap) {
        byte[] longerPiece = first.length > second.length ? first : second;
        byte[] shorterPiece = first.length > second.length ? second : first;
        
        byte[] retval;
        if (shouldWrap) {
            retval = new byte[longerPiece.length];
        } else {
            retval = new byte[shorterPiece.length];
        }
        
        for (int i = 0; i < retval.length; i++) {
            retval[i] = (byte) (longerPiece[i] ^ shorterPiece[i % shorterPiece.length]);
        }
        return retval;
    }
}
