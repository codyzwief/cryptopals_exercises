package com.czwief.crypto.one;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import junit.framework.Assert;

import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author cody
 */
public class HexUtils {
    
    /**
     * Turn a hex-formatted string into its Base64 representation.
     * 
     * @param hexString a String in hexadecimal form (e.g. 4afb599d...)
     * @return the string in Base64 encoded format
     * @throws Base64DecodingException 
     */
    public static String hexToBase64(final String hexString) {
        final HexEncodedString hexInfo = new HexEncodedString(hexString);
        return hexInfo.getBase64String();
    }
    
    public static String xorHexStringsDifferentLength(final String longerString,
            final String shorterString) {
        //make sure the strings are the correct 
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
