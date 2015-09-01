/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.hex;

import com.czwief.crypto.one.scorer.StringScorer;
import junit.framework.Assert;
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
            String testString = xorHexStrings(hexToDecode, hexCharacter).getDisplayString();
           
            int score = StringScorer.scoreString(testString);
            if (score > topScore) {
                topScore = score;
                topScoreString = testString;
            }
        }
        
        return topScoreString;
    }
    
    /**
     * XOR two Strings together that are String representations of hex.
     * 
     * If the strings are of different length, the longer string must be passed in as the first argument.
     * 
     * @param longerString
     * @param shorterString
     * @return 
     */
    public static HexEncodedString xorHexStrings(final String longerString, final String shorterString) throws DecoderException {
        //make sure the strings are the correct...for now
        Assert.assertEquals("You must pad strings that have indivisible lengths: " + longerString.length() + " & " + shorterString.length(), 
                0, longerString.length() % shorterString.length());
        
        final HexEncodedString hexInfo = new HexEncodedString(longerString);
        final HexEncodedString xorWithHexInfo = new HexEncodedString(shorterString);
        final byte[] hexBytes = hexInfo.getHexBytes();
        final byte[] xorWithBytes = xorWithHexInfo.getHexBytes();
        
        return new HexEncodedString(Hex.encodeHexString(xorBytes(hexBytes, xorWithBytes, true)));
        
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
