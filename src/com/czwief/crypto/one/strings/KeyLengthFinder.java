package com.czwief.crypto.one.strings;

import java.util.Arrays;

/**
 *
 * @author cody
 */
public class KeyLengthFinder {
    
    /**
     * Attempt to determine the appropriate key size of a block of cipher text.
     * 
     * @param cipherText
     * @return 
     */
    public static int findKeySize(final byte[] cipherText, final int smallestGuess, final int largestGuess) {
        int bestKeysize = Integer.MIN_VALUE;
        long mostSimilarKeysize = Long.MAX_VALUE;
        
        for (int singleKeysizeAttempt = smallestGuess; singleKeysizeAttempt <= largestGuess; singleKeysizeAttempt++) {
            int totalDistanceBetween = 0, totalComparisons = 0;
            int numberOfGuessesToMake = (cipherText.length / singleKeysizeAttempt) - 1;
            
            for (int i = 0; i < numberOfGuessesToMake; i+=2) {
                byte[] firstBlock = Arrays.copyOfRange(cipherText, i * singleKeysizeAttempt, (i+1) * singleKeysizeAttempt);
                byte[] secondBlock = Arrays.copyOfRange(cipherText, (i+1) * singleKeysizeAttempt, (i+2) * singleKeysizeAttempt);    
                totalDistanceBetween += StringDistance.determineDistanceBetween(firstBlock, secondBlock);
                totalComparisons++;
            }
            
            final long averageDifferenceBetween = ((long) totalDistanceBetween / (long) totalComparisons);
            final long normalizedDifferenceBetween = (averageDifferenceBetween / (long) singleKeysizeAttempt);
            
            if (normalizedDifferenceBetween < mostSimilarKeysize) {
                mostSimilarKeysize = normalizedDifferenceBetween;
                bestKeysize = singleKeysizeAttempt;
            }
        }
        
        return bestKeysize;
    }
    
    /**
     * Attempt to determine the appropriate key size of a block of cipher text.
     * 
     * @param cipherText
     * @return 
     */
    public static int findKeySize(final byte[] cipherText) {
        return findKeySize(cipherText, 2, 64);
        
    }
    
    
}
