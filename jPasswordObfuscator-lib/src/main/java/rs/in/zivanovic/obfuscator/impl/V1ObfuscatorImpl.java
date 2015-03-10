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

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * PBE-based obfuscator using <em>PBEWithSHA256And128BitAES</em> algorithm with 16,000 rounds, 8 bytes of randomly
 * generated salt and BouncyCastle as crypto provider.
 */
public class V1ObfuscatorImpl extends PBEObfuscatorImpl {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
    private static final int VERSION = 1;
    private static final String PROVIDER = "BC";
    private static final String ALGO = "PBEWithSHA256And128BitAES-CBC-BC";
    private static final int SALT_LEN = 8;
    private static final int ITERATION_COUNT = 16_000;

    public V1ObfuscatorImpl() {
        super(VERSION, ALGO, PROVIDER, ITERATION_COUNT, SALT_LEN);
    }

}
