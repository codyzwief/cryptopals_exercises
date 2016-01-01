package com.czwief.crypto.utils;

import com.czwief.crypto.one.strings.StringScorer;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author cody
 */
public class XorUtils {
        
    /**
     * TODO: put this in a place where it makes more sense
     * 
     * @param hexToDecode
     * @param shouldReturnKeyInstead
     * @return 
     */
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
    
    /**
     * TODO: Put this in a spot where it makes more sense
     * 
     * @param hexStrings
     * @param blockSize
     * @return 
     */
    public static String determineAESWithECB(final List<String> hexStrings, int blockSize) throws DecoderException {
        String bestString = null;
        int bestMatches = 0;
        for (String hexString : hexStrings) {
            byte[] bytes = HexUtils.toByteArray(hexString);
            final int numBlocks = bytes.length / blockSize;
            int numMatches = 0;
            for (int i = 0; i < numBlocks; i++) {
                byte[] theBlock = Arrays.copyOfRange(bytes, i * blockSize, (i+1) * blockSize);
                for (int j = i + 1; j < numBlocks; j++) {
                    byte[] theBlockToCompareTo = Arrays.copyOfRange(bytes, j * blockSize, (j + 1) * blockSize);
                    if (Arrays.equals(theBlock, theBlockToCompareTo)) {
                        numMatches++;
                    }
                }
            }
            if (numMatches > bestMatches) {
                bestMatches = numMatches;
                bestString = hexString;
            }
        }
        return bestString;
    }
    
    public static byte[] xorBytes(final byte[] first, final byte[] second) {
        return xorBytes(first, second, true);
    }
    
    
    public static byte[] xorBytes(byte[] first, byte[] second, boolean shouldWrap) {
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
