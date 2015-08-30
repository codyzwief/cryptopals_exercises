package com.czwief.crypto.one.hex;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import java.nio.charset.Charset;
import junit.framework.Assert;
import org.apache.commons.codec.DecoderException;

import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author cody
 */
public final class HexUtils {
    
    public static String toHex(String str) {
        return Hex.encodeHexString(str.getBytes(Charset.defaultCharset()));
    }
    
    public static String toString(String hex) throws DecoderException {
        return new String(Hex.decodeHex(hex.toCharArray()));
    }
    
    /**
     * Turn a hex-formatted string into its Base64 representation.
     * 
     * @param hexString a String in hexadecimal form (e.g. 4afb599d...)
     * @return the string in Base64 encoded format
     * @throws Base64DecodingException 
     */
    public static String hexToBase64(final String hexString) throws DecoderException {
        final HexEncodedString hexInfo = new HexEncodedString(hexString);
        return hexInfo.getBase64String();
    }
    
    public static HexEncodedString encrypt(final String text, final String key)  throws DecoderException {
        HexEncodedString textToEncrypt = new HexEncodedString(text.getBytes());
        HexEncodedString keyToEncrypt = new HexEncodedString(key.getBytes());
        
        System.out.println("text = " + textToEncrypt.getHexString());
        System.out.println("key = " + keyToEncrypt.getHexString());
        
        StringBuilder finalString = new StringBuilder();
        
        for (int i = 0; i < textToEncrypt.getHexString().length(); i+=2) {
            String tempResult = textToEncrypt.getHexString().substring(i, i+2);
            for (int j = 0; j < keyToEncrypt.getHexString().length(); j+=2) {
                String tempKey = keyToEncrypt.getHexString().substring(j, j+2);
                tempResult = xorHexStrings(tempResult, tempKey).getHexString();
            }
            finalString.append(tempResult);
        }
        
        return new HexEncodedString(finalString.toString());
    }
    
    /**
     * XOR two Strings together that are String representations of hex.
     * 
     * If the strings are of different length, the longer string must be passed in as the first argument.
     * 
     * I'll refactor this later.
     * 
     * @param longerString
     * @param shorterString
     * @return 
     */
    public static HexEncodedString xorHexStrings(final String longerString, final String shorterString) throws DecoderException {
        //make sure the strings are the correct...for now
        Assert.assertEquals("You must pad strings that have indivisible lengths: " + longerString.length() + " & " + shorterString.length(), 
                0, longerString.length() % shorterString.length());
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < longerString.length() / shorterString.length(); i+= shorterString.length()) {
            sb.append(produceXorString(longerString.substring(i, shorterString.length() + i), shorterString));
        }
        return new HexEncodedString(sb.toString());
    }
    
    private static String produceXorString(final String string, final String xorWith) throws DecoderException {
        final HexEncodedString hexInfo = new HexEncodedString(string);
        final HexEncodedString xorWithHexInfo = new HexEncodedString(xorWith);
        final byte[] hexBytes = hexInfo.getHexBytes();
        final byte[] xorWithByes = xorWithHexInfo.getHexBytes();
        Assert.assertEquals(hexBytes.length, xorWithByes.length);
        
        byte[] retVal = new byte[hexBytes.length];
        
        for (int i = 0; i < hexBytes.length; i++) {
            retVal[i] = new Integer(hexBytes[i] ^ xorWithByes[i]).byteValue();
        }
        return Hex.encodeHexString(retVal);
    }
    
}
