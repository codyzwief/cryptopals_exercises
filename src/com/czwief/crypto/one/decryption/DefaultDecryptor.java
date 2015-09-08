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

    @Override
    public String decrypt(String ciphertext, String key) throws DecoderException {
        HexEncodedString plaintextEncoded = HexEncodedStringGenerator.generateFromHex(ciphertext);
        HexEncodedString keyEncoded = HexEncodedStringGenerator.generateFromPlaintext(key);
        byte[] textHexBytes = plaintextEncoded.getHexBytes();
        byte[] keyHexBytes = keyEncoded.getHexBytes();
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < textHexBytes.length; i++) {
            byte singleByte = (byte) (textHexBytes[i] ^ keyHexBytes[i % keyHexBytes.length]);
            sb.append(new String(new byte[]{singleByte}));
        }
        
        return sb.toString();
        
    }

    @Override
    public String decrypt(byte[] ciphertext, String key) throws DecoderException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
