/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.distance;

import junit.framework.Assert;

/**
 *
 * @author cody
 */
public class StringDistance {
    
    public static int determineDistanceBetween(String one, String two) {
        Assert.assertEquals("String do not have equal lengths", one.length(), two.length());
        int distance = 0;
        byte[] oneBytes = one.getBytes();
        byte[] twoBytes = two.getBytes();
        for (int i = 0; i < oneBytes.length; i++) {
            distance += Integer.bitCount(oneBytes[i] ^ twoBytes[i]);
        }
        
        return distance;
    }
    
}
