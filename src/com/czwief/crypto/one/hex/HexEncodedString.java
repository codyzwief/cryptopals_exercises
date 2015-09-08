package com.czwief.crypto.one.hex;

import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * A simple wrapper around transitioning a hex string to a base64 encoded string.
 * 
 * @author cody
 */
public class HexEncodedString {
    private final byte[] encodedHexBytes;
    private final byte[] displayStringBytes;
    private final String hexString;
    private final String displayString;
    private final String base64String;
    
    public HexEncodedString(final String hexString) throws DecoderException {
        Init.init();
        this.hexString = hexString;
        this.encodedHexBytes = DatatypeConverter.parseHexBinary(hexString);
        this.base64String = Base64.encode(encodedHexBytes);
        this.displayStringBytes = Hex.decodeHex(hexString.toCharArray());
        this.displayString = new String(displayStringBytes);
    }
    
    public HexEncodedString(final byte[] displayBytes) throws DecoderException {
        this.displayStringBytes = displayBytes;
        this.displayString = new String(displayBytes);
        this.hexString = String.copyValueOf(Hex.encodeHex(displayBytes));
        this.encodedHexBytes = Hex.decodeHex(hexString.toCharArray());
        this.base64String = Base64.encode(encodedHexBytes);
    }
    
    public String getHexString() {
        return hexString;
    }
    
    public String getBase64String() {
        return base64String;
    }
    
    public byte[] getHexBytes() {
        return encodedHexBytes;
    }
    
    public byte[] getDisplayStringBytes() {
        return displayStringBytes;
    }
    
    public String getDisplayString() {
        return displayString;
    }
}
