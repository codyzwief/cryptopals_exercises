package com.czwief.crypto.one.hex;

import java.nio.charset.Charset;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author cody
 */
public final class HexEncodedStringGenerator {
    
    public static HexEncodedString generate(final String plaintext) throws DecoderException {
        return new HexEncodedString(plaintext.getBytes(Charset.defaultCharset()));
    }
    
    public static HexEncodedString generateFromHex(final String hex) throws DecoderException {
        return new HexEncodedString(hex);
    }
}
