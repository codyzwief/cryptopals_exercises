package com.czwief.crypto.encryption.impl;

import com.czwief.crypto.encryption.Encryptor;
import com.czwief.crypto.encryption.GenericEncryptionDecryptionUtility;
import com.czwief.crypto.utils.Base64Utils;
import com.czwief.crypto.utils.EncryptionMode;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Handrolling manual CBC mode 
 * 
 * @author cody
 */
public class RandomStringAppendingEncryptor implements Encryptor {
    private static final byte[] hardcodedKeyWeDontKnow = "HELLO DEAD WORLD".getBytes(); //TODO make this randomly generated
    private final Encryptor encryptor = new GenericEncryptionDecryptionUtility("AES/ECB/PKCS5Padding", EncryptionMode.ENCRYPT);
    public static final byte[] TWO_POINT_TWELVE = Base64Utils.decode("Um9sbGluJyBpbiBteSA1LjAKV2l0aCBteSByYWctdG9wIGRvd24gc28gbXkg\n" +
            "aGFpciBjYW4gYmxvdwpUaGUgZ2lybGllcyBvbiBzdGFuZGJ5IHdhdmluZyBq\n" +
            "dXN0IHRvIHNheSBoaQpEaWQgeW91IHN0b3A/IE5vLCBJIGp1c3QgZHJvdmUg\n" +
            "YnkK");

    @Override
    public byte[] encrypt(final byte[] plaintext, final byte[] keyThatIsIgnored, final byte[] ivThatIsIgnored) {
        final byte[] fullPlainText = ArrayUtils.addAll(plaintext, TWO_POINT_TWELVE);
        return encryptor.encrypt(fullPlainText, hardcodedKeyWeDontKnow, null);
    }
}
