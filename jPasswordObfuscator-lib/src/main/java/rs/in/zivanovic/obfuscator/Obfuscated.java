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
package rs.in.zivanovic.obfuscator;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import rs.in.zivanovic.obfuscator.impl.ObfuscatedData;
import rs.in.zivanovic.obfuscator.impl.V1ObfuscatorImpl;

/**
 * Wrapper around sensitive data that performs obfuscation using secret key and generate output string. For the sake of
 * security, you should zero your secret key data as soon as possible after constructing Obfuscated object. Objects of
 * this class do not retain any sensitive data after being constructed.
 */
public class Obfuscated {

    private final ObfuscatedData obfuscatedData;

    /**
     * Build new obfuscation wrapper using latest obfuscation algorithm version.
     *
     * @param key master key to use for obfuscation
     * @param data data to obfuscate
     */
    public Obfuscated(char[] key, String data) {
        this(key, data.getBytes(StandardCharsets.UTF_8), 1);
    }

    /**
     * Build new obfuscation wrapper using latest obfuscation algorithm version.
     *
     * @param key master key to use for obfuscation
     * @param data data to obfuscate
     */
    public Obfuscated(char[] key, byte[] data) {
        this(key, data, 1);
    }

    /**
     * Build new obfuscation wrapper using specified obfuscation algorithm version.
     *
     * @param key master key to use for obfuscation
     * @param data data to obfuscate
     * @param version version of the obfuscation algorithm to use
     */
    public Obfuscated(char[] key, byte[] data, int version) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(data);
        Objects.requireNonNull(version);
        switch (version) {
            case 1:
                this.obfuscatedData = new V1ObfuscatorImpl().obfuscate(key, data);
                break;
            default:
                throw new IllegalArgumentException("Unsupported version: " + version);
        }
    }

    @Override
    public String toString() {
        return obfuscatedData.toString();
    }

}
