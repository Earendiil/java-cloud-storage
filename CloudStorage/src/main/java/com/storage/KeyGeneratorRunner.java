package com.storage;

import com.storage.util.FileEncryptionUtil;

public class KeyGeneratorRunner {
    public static void main(String[] args) throws Exception {
        String key = FileEncryptionUtil.generateNewBase64Key();
        System.out.println("Generated AES-256 Key (Base64):");
        System.out.println(key);
    }
}
