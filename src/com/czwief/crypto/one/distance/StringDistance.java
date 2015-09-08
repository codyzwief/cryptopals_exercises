package com.czwief.crypto.one.distance;

import junit.framework.Assert;

/**
 *
 * @author cody
 */
public class StringDistance {
    
    public static int determineDistanceBetween(final String one, final String two) {
        Assert.assertEquals("String do not have equal lengths", one.length(), two.length());
        return determineDistanceBetween(one.getBytes(), two.getBytes());
    }
    
    public static int determineDistanceBetween(final byte[] one, final byte[] two) {
        int distance = 0;
        for (int i = 0; i < one.length; i++) {
            distance += Integer.bitCount(one[i] ^ two[i]);
        }
        return distance;
    }
    
}
