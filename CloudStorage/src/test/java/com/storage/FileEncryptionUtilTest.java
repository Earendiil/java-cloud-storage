package com.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.storage.util.FileEncryptionUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class FileEncryptionUtilTest {
	@Autowired
    private FileEncryptionUtil fileEncryptionUtil;

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        String originalContent = "This is a secret message.";
        byte[] originalBytes = originalContent.getBytes();

        byte[] encrypted = fileEncryptionUtil.encrypt(originalBytes);
        assertNotEquals(new String(encrypted), originalContent, "Encrypted content should not match original");

        byte[] decrypted = fileEncryptionUtil.decrypt(encrypted);
        String decryptedContent = new String(decrypted);

        assertEquals(originalContent, decryptedContent, "Decrypted content should match original");
    }
}
