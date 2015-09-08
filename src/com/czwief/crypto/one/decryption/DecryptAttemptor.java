package com.czwief.crypto.one.decryption;

import com.czwief.crypto.one.encryption.DefaultEncryptor;
import com.czwief.crypto.one.hex.Base64Utils;
import com.czwief.crypto.one.hex.HexEncodedString;
import com.czwief.crypto.one.hex.HexEncodedStringGenerator;
import com.czwief.crypto.one.hex.XorUtils;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author cody
 */
public class DecryptAttemptor implements Decryptor {
    
    DefaultDecryptor decryptor = new DefaultDecryptor();
    DefaultEncryptor encryptor = new DefaultEncryptor();

    /**
     * Decrypt the ciphertext as specified in 1.6. This ignores the key.
     * 
     * http://cryptopals.com/sets/1/challenges/6/
     * 
     * @param ciphertext
     * @param key
     * @return
     * @throws DecoderException 
     */
    @Override
    public String decrypt(String ciphertext, String key) throws DecoderException {
        System.out.println("CIPHER TEXT = " + Hex.encodeHexString(Base64Utils.decode(ciphertext)));
        HexEncodedString cipherTextEncoded = new HexEncodedString(Base64Utils.decode(ciphertext));
        
        
        //1. Since we're ignoring the key, attempt to figure out the key.
        // Do this by determining a keysize
        Integer keySize = KeyFinder.findKeySize(cipherTextEncoded);
        //Assert.assertEquals("Key size of " + keySize + " should be even.", 0, keySize % 2);
        System.out.println("KEYSIZE IS = " + keySize);
        
        //2. Break the ciphertext into blocks of KEYSIZE each.
        
        //for (int singleKeySize = 2; singleKeySize < 41; singleKeySize++) {
        
        int tempIndex = 0;
        final byte[] cipherTextBytes = cipherTextEncoded.getDisplayStringBytes();
        Integer temp = (int) Math.ceil((double) cipherTextBytes.length / (double) keySize);
        byte[][] blocks = new byte[temp][keySize];
        for (int i = 0; i < cipherTextBytes.length; i++) {
            if (tempIndex >= keySize) {
                tempIndex = 0;
            }
            blocks[i / keySize][tempIndex++] = cipherTextBytes[i];
        }
        
        //3. Transpose the blocks... make each block the first byte of each block
        blocks = transpose(blocks);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < blocks.length; i++) {
            sb.append(XorUtils.attemptSingleDecryption(Hex.encodeHexString(blocks[i]), true));
        }
        
        String result = decryptor.decrypt(cipherTextEncoded.getHexString(), sb.toString());
        return result;
    }
    
    private byte[][] transpose(final byte[][] originalMatrix) {
        byte[][] retVal = new byte[originalMatrix[0].length][originalMatrix.length];
        
        for(int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix[0].length; j++) {
                retVal[j][i] = originalMatrix[i][j];
            }
        }
        
        return retVal;
    }

    @Override
    public String decrypt(byte[] ciphertext, String key) throws DecoderException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
