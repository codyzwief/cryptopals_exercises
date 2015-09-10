package com.czwief.crypto.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Hex;

/**
 * Utilities that help perform some Base64 encoding and decoding duties.
 * 
 * @author cody
 */
public final class Base64Utils {
    
    public static byte[] decode(final String base64) {
        return Base64.decode(base64);
    }
    
    public static String toHex(final String base64) {
        return Hex.encodeHexString(decode(base64));
    }
    
    public static String toBase64(final String hex) {
        return Base64.encode(DatatypeConverter.parseHexBinary(hex));
    }
}
