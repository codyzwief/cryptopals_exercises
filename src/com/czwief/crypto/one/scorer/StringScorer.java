package com.czwief.crypto.one.scorer;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple utility class to help determine the 'english-ness' of a particular word or phrase.
 * 
 * This doesn't really do anything complicated.
 * 
 * TODO: Better support for the capitalization patterns of words/phrases.
 * 
 * @author cody
 */
public class StringScorer {
    
    //Statistics stolen from http://www.macfreek.nl/memory/Letter_Distribution
    private static final Map<Character, Integer> LETTER_FREQUENCIES = new HashMap<Character, Integer>(){{
        put('a', 65);
        put('b', 13);
        put('c', 22);
        put('d', 33);
        put('e', 103);
        put('f', 20);
        put('g', 16);
        put('h', 50);
        put('i', 57);
        put('j', 1);
        put('k', 6);
        put('l', 33);
        put('m', 20);
        put('n', 57);
        put('o', 62);
        put('p', 15);
        put('q', 1);
        put('r', 50);
        put('s', 53);
        put('t', 75);
        put('u', 23);
        put('v', 8);
        put('w', 17);
        put('x', 1);
        put('y', 14);
        put('z', 1);
        put(' ', 183); //spaces too!
    }};
    
    public static int scoreString(final String stringToScore) {
        final String lowerCaseStringToScore = stringToScore.toLowerCase();
        int stringScore = 0;
        for (int i = 0; i < lowerCaseStringToScore.length(); i++) {
            char singleCharacter = lowerCaseStringToScore.charAt(i);
            if (LETTER_FREQUENCIES.containsKey(singleCharacter)) {
                stringScore += LETTER_FREQUENCIES.get(singleCharacter);
            }
        }
        return stringScore;
    }
    
}
