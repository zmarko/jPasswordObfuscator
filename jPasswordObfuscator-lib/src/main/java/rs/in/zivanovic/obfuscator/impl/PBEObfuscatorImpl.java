/*
 * The MIT License
 *
 * Copyright 2015 Marko Zivanovic <marko@zivanovic.in.rs>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package rs.in.zivanovic.obfuscator.impl;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;

/**
 * Password Based Encryption (PBE) based obfuscator. Use as a base class for further specializations.
 */
public class PBEObfuscatorImpl implements Obfuscator {

    private final int version;
    private final String algo;
    private final String provider;
    private final int iterations;
    private final int saltLen;

    /**
     * Build new PBE-based obfuscator with specified parameters.
     *
     * @param version    version number for this set of parameters
     * @param algo       encryption algorithm to use
     * @param provider   crypto provider
     * @param iterations number of key derivation rounds
     * @param saltLen    length of random salt in bytes
     */
    public PBEObfuscatorImpl(int version, String algo, String provider, int iterations, int saltLen) {
        this.algo = algo;
        this.provider = provider;
        this.iterations = iterations;
        this.saltLen = saltLen;
        this.version = version;
    }

    @Override
    public ObfuscatedData obfuscate(char[] masterKey, byte[] data) {
        try {
            byte[] salt = generateRandomSalt();
            byte[] cipher = crypto(Cipher.ENCRYPT_MODE, data, masterKey, salt);
            return new ObfuscatedData(version, salt, cipher);
        } catch (GeneralSecurityException ex) {
            throw new ObfuscatorException(ex);
        }
    }

    @Override
    public byte[] unObfuscate(char[] masterKey, ObfuscatedData ob) {
        try {
            return crypto(Cipher.DECRYPT_MODE, ob.getCipherText(), masterKey, ob.getSalt());
        } catch (GeneralSecurityException ex) {
            throw new ObfuscatorException(ex);
        }
    }

    private byte[] crypto(int mode, byte[] input, char[] masterKey, byte[] salt) throws IllegalBlockSizeException,
            InvalidKeyException, NoSuchAlgorithmException, BadPaddingException, NoSuchProviderException,
            NoSuchPaddingException, InvalidKeySpecException {
        SecretKeyFactory skf = SecretKeyFactory.getInstance(algo, provider);
        PBEKeySpec ks = new PBEKeySpec(masterKey, salt, iterations);
        SecretKey sk = skf.generateSecret(ks);
        Cipher c = Cipher.getInstance(algo, provider);
        c.init(mode, sk);
        return c.doFinal(input);
    }

    private byte[] generateRandomSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        Random random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[saltLen];
        random.nextBytes(salt);
        return salt;
    }

}
