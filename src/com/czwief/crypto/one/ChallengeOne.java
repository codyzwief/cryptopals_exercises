/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.czwief.crypto.one;

import com.sun.org.apache.xml.internal.security.Init;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author cody
 */
public class ChallengeOne {
    
    public static String hexToBase64(final String hex) throws Base64DecodingException {
        Init.init();
        byte[] encodedHex = DatatypeConverter.parseHexBinary(hex);
        return Base64.encode(encodedHex);
    }
    
}
