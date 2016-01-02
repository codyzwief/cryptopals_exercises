package com.czwief.crypto.utils;

import java.util.Random;

/**
 *
 * @author cody
 */
public class KeyGenerationUtils {
    
    public static byte[] generateAESKey(final int keySize) {
        final byte[] retval = new byte[keySize];
        
        Random rand = new Random();
        for (int i = 0; i < retval.length; i++) {
            retval[i] = (byte) rand.nextInt();
        }
        
        return retval;
    }
}
