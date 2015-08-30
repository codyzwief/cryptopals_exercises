/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one.encryption;

import org.apache.commons.codec.DecoderException;

/**
 *
 * @author cody
 */
public interface Encryptor {
    
    String encrypt(String plaintext, String key) throws DecoderException;
    
}
