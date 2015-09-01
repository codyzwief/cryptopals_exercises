/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.hex;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author cody
 */
public final class Base64Utils {
    
    /**
     * Turn a hex-formatted string into its Base64 representation.
     * 
     * @param hexString a String in hexadecimal form (e.g. 4afb599d...)
     * @return the string in Base64 encoded format
     * @throws Base64DecodingException 
     */
    public static String toBase64(final String hex) throws DecoderException {
        return HexEncodedStringGenerator.generateFromHex(hex).getBase64String();
    }
    
    public static String toHex(final String base64) {
        return Hex.encodeHexString(Base64.decode(base64));
    }
    
    public static byte[] decode(final String base64) {
        return Base64.decode(base64);
    }
}
