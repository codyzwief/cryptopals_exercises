package com.czwief.crypto.one.decryption;

import com.czwief.crypto.one.encryption.*;
import com.czwief.crypto.one.hex.HexEncodedString;
import com.czwief.crypto.one.hex.HexEncodedStringGenerator;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.DecoderException;

/**
 *
 * @author cody
 */
public class DefaultDecryptor implements Decryptor {

    //TODO implement
    @Override
    public String decrypt(String plaintext, String key) throws DecoderException {
        HexEncodedString plaintextEncoded = HexEncodedStringGenerator.generate(plaintext);
        HexEncodedString keyEncoded = HexEncodedStringGenerator.generate(key);
        byte[] textHexBytes = plaintextEncoded.getHexBytes();
        byte[] keyHexBytes = keyEncoded.getHexBytes();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < textHexBytes.length; i++) {
            byte singleByte = (byte) (textHexBytes[i] ^ keyHexBytes[i % keyHexBytes.length]);
            sb.append(DatatypeConverter.printHexBinary(new byte[]{singleByte}));
        }
        
        return sb.toString().toLowerCase();
        
    }
    
}
