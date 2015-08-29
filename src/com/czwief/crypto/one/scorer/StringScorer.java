/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.scorer;

/**
 *
 * @author cody
 */
public class StringScorer {
    
    private static final String VOWELS = "aeiouAEIOU";
    private static final int VOWEL_SCORE = 3;
    private static final int CONSONANT_SCORE = 1;
    
    public static int scoreString(final String stringToScore) {
        int numChars = 0;
        for (int i = 0; i < stringToScore.length(); i++) {
            char s = stringToScore.charAt(i);
            if (VOWELS.contains(stringToScore.subSequence(i, i + 1))) {
                numChars += VOWEL_SCORE;
            } else if (Character.isLetter(s)) {
                numChars += CONSONANT_SCORE;
            }
        }
        return numChars;
    }
    
}
