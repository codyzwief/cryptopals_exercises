/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.decryption;

import com.czwief.crypto.one.distance.StringDistance;
import com.czwief.crypto.one.hex.HexEncodedString;

/**
 *
 * @author cody
 */
public class KeyFinder {
    
    public static int findKeySize(HexEncodedString cipherText) {
        int BEST_KEYSIZE = -1;
        long best_ratio = Long.MAX_VALUE;
        
        final String cipherTextString = cipherText.getDisplayString();
        
        for (int TEMP_KEYSIZE = 2; TEMP_KEYSIZE <= 40; TEMP_KEYSIZE++) {
            final String sectionOne = cipherTextString.substring(0, TEMP_KEYSIZE);
            final String sectionTwo = cipherTextString.substring(TEMP_KEYSIZE, TEMP_KEYSIZE * 2);
            final int differenceBetween = StringDistance.determineDistanceBetween(sectionOne, sectionTwo);
            final String sectionThree = cipherTextString.substring(TEMP_KEYSIZE * 2, TEMP_KEYSIZE * 3);
            final String sectionFour = cipherTextString.substring(TEMP_KEYSIZE * 3, TEMP_KEYSIZE * 4);
            final int differenceBetweenTwo = StringDistance.determineDistanceBetween(sectionThree, sectionFour);
            final long averageDifferenceBetween = ((long)(differenceBetween + differenceBetweenTwo)) / 2L;
            final long normalizedDifferenceBetween = (averageDifferenceBetween / (long) TEMP_KEYSIZE);
            if (normalizedDifferenceBetween < best_ratio) {
                best_ratio = normalizedDifferenceBetween;
                BEST_KEYSIZE = TEMP_KEYSIZE;
            }
        }
        
        return BEST_KEYSIZE;
        
    }
    
    
}
