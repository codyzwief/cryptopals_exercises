package com.czwief.crypto.one.hex;

import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author cody
 */
public final class HexEncodedStringGenerator {
    
    /**
     * Generate an encoded object from a plaintext string.
     * 
     * @param plaintext
     * @return
     * @throws DecoderException 
     */
    public static HexEncodedString generateFromPlaintext(final String plaintext) throws DecoderException {
        return new HexEncodedString(plaintext.getBytes(Charset.defaultCharset()));
    }
    
    /**
     * Generate an encoded object from a hex string (e.g. "0fd34db33f")
     * 
     * @param hex
     * @return
     * @throws DecoderException 
     */
    public static HexEncodedString generateFromHex(final String hex) throws DecoderException {
        return new HexEncodedString(hex);
    }
}
