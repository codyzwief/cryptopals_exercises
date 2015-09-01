package com.czwief.crypto.one.hex;

import java.nio.charset.Charset;
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
}
