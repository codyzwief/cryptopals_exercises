package com.czwief.crypto.utils;

import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;

import org.apache.commons.codec.binary.Hex;

/**
 * Some utilities to help with the transition from Hex strings to actual strings,
 * and vice versa.
 * 
 * @author cody
 */
public final class HexUtils {
    
    public static String toHex(String str) {
        return Hex.encodeHexString(str.getBytes(Charset.defaultCharset()));
    }
    
    public static byte[] toByteArray(final String hex) throws DecoderException {
        return Hex.decodeHex(hex.toCharArray());
    }
    
    public static String toString(String hex) throws DecoderException {
        return new String(toByteArray(hex));
    }
    
    /**
     * XOR two Strings together that are String representations of hex.
     * Return the hex string that represents the XOR of those two hex strings.
     * 
     * If the strings are of different length, the longer string must be passed in as the first argument.
     * 
     * @param longerString
     * @param shorterString
     * @return 
     */
    public static String xorHexStrings(final String first, final String second) throws DecoderException {
        
        final byte[] firstAsEncoded = toByteArray(first);
        final byte[] secondAsEncoded = toByteArray(second);
        
        return Hex.encodeHexString(XorUtils.xorBytes(firstAsEncoded, secondAsEncoded));
    }
}
