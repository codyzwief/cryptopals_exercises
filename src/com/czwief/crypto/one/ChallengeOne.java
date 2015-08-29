/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import junit.framework.Assert;

import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author cody
 */
public class ChallengeOne {
    
    /**
     * 
     * @param hex
     * @return
     * @throws Base64DecodingException 
     */
    public static String hexToBase64(final String hex) throws Base64DecodingException {
        HexEncodedString hexInfo = new HexEncodedString(hex);
        return hexInfo.getBase64String();
    }
    
    public static String xorHexStringsDifferentLength(final String longerString,
            final String shorterString) {
        Assert.assertEquals(0, longerString.length() % shorterString.length());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < longerString.length() / shorterString.length(); i+= shorterString.length()) {
            sb.append(xorHexStrings(longerString.substring(i, shorterString.length() + i), shorterString));
        }
        return new HexEncodedString(sb.toString()).getDisplayString();
    }
    
    public static String xorHexStrings(final String string, final String xorWith) {
        HexEncodedString hexInfo = new HexEncodedString(string);
        HexEncodedString xorWithHexInfo = new HexEncodedString(xorWith);
        byte[] hexBytes = hexInfo.getHexBytes();
        byte[] xorWithByes = xorWithHexInfo.getHexBytes();
        Assert.assertEquals(hexBytes.length, xorWithByes.length);
        
        byte[] retVal = new byte[hexBytes.length];
        
        for (int i = 0; i < hexBytes.length; i++) {
            retVal[i] = new Integer(hexBytes[i] ^ xorWithByes[i]).byteValue();
        }
        return Hex.encodeHexString(retVal);
    }
    
}
