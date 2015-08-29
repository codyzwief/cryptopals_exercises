/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazon.crypto.one;

import com.czwief.crypto.one.ChallengeOne;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cody
 */
public class ChallengeOneTest {
    
    public ChallengeOneTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void cryptoTest() throws Exception {
        String hex =
                "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
        System.out.println(hex.length());
        Assert.assertEquals(
                "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t",
                ChallengeOne.hexToBase64(hex));
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}