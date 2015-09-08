/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.decryption;

import com.czwief.crypto.one.distance.StringDistance;
import com.czwief.crypto.one.hex.HexEncodedString;
import java.util.Arrays;

/**
 *
 * @author cody
 */
public class KeyFinder {
    
    public static int findKeySize(HexEncodedString cipherText) {
        int BEST_KEYSIZE = -1;
        long best_ratio = Long.MAX_VALUE;
        
        final byte[] cipherTextString = cipherText.getHexBytes();
        
        for (int TEMP_KEYSIZE = 2; TEMP_KEYSIZE <= 40; TEMP_KEYSIZE++) {
            
            int totalDistanceBetween = 0;
            int numBlocks = 0;
            for (int j = 0; j < cipherTextString.length / TEMP_KEYSIZE - 1; j+=2) {
                byte[] firstBlock = Arrays.copyOfRange(cipherTextString, j * TEMP_KEYSIZE, (j+1) * TEMP_KEYSIZE);
                byte[] secondBlock = Arrays.copyOfRange(cipherTextString, (j+1) * TEMP_KEYSIZE, (j+2) * TEMP_KEYSIZE);
                
                totalDistanceBetween += StringDistance.determineDistanceBetween(firstBlock, secondBlock);
                numBlocks++;
            }
            
            
            //final byte[] sectionOne = Arrays.copyOfRange(cipherTextString, 0, TEMP_KEYSIZE);
            //final byte[] sectionTwo = Arrays.copyOfRange(cipherTextString, TEMP_KEYSIZE, TEMP_KEYSIZE * 2);
            //final int differenceBetween = StringDistance.determineDistanceBetween(sectionOne, sectionTwo);
            //final byte[] sectionThree = Arrays.copyOfRange(cipherTextString, TEMP_KEYSIZE * 2, TEMP_KEYSIZE * 3);
            //final byte[] sectionFour = Arrays.copyOfRange(cipherTextString, TEMP_KEYSIZE * 3, TEMP_KEYSIZE * 4);
            //final int differenceBetweenTwo = StringDistance.determineDistanceBetween(sectionThree, sectionFour);
            final long averageDifferenceBetween = ((long) totalDistanceBetween / (long) numBlocks);
            final long normalizedDifferenceBetween = (averageDifferenceBetween / (long) TEMP_KEYSIZE);
            
            
            if (normalizedDifferenceBetween < best_ratio) {
                best_ratio = normalizedDifferenceBetween;
                BEST_KEYSIZE = TEMP_KEYSIZE;
            }
        }
        
        return BEST_KEYSIZE;
        
    }
    
    
}
