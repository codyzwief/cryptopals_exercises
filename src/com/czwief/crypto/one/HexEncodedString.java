package com.czwief.crypto.one;

import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import javax.xml.bind.DatatypeConverter;

/**
 * A simple wrapper around transitioning a hex string to a base64 encoded string.
 * 
 * @author cody
 */
public class HexEncodedString {
    private final byte[] encodedHex;
    private final String hexString;
    private final String base64String;
    
    public HexEncodedString(final String hexString) {
        Init.init();
        this.hexString = hexString;
        this.encodedHex = DatatypeConverter.parseHexBinary(hexString);
        this.base64String = Base64.encode(encodedHex);
    }
    
    public String getHexString() {
        return hexString;
    }
    
    public String getBase64String() {
        return base64String;
    }
    
    public byte[] getHexBytes() {
        return encodedHex;
    }
    
    public String getDisplayString() {
        return new String(getHexBytes());
    }
}
