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

import java.util.Objects;
import rs.in.zivanovic.obfuscator.impl.ObfuscatedData;
import rs.in.zivanovic.obfuscator.impl.Obfuscator;
import rs.in.zivanovic.obfuscator.impl.V1ObfuscatorImpl;

/**
 * Obfuscate and un-obfuscate sensitive data.
 *
 * This method is useful for protecting potentially sensitive data from casual onlookers. It is NOT secure against
 * attackers with access to source code or live systems.
 */
public final class JPasswordObfuscator {

    private final Obfuscator v1Obfuscator = new V1ObfuscatorImpl();

    /**
     * Obfuscate data using supplied master key and current algorithm version.
     *
     * @param masterKey master key to use for obfuscation
     * @param data      data to obfuscate
     *
     * @return string containing obfuscated data; use {@link #unObfuscate} to get secret data from this string
     */
    public String obfuscate(char[] masterKey, byte[] data) {
        return obfuscate(masterKey, data, 1);
    }

    /**
     * Obfuscate data using supplied master key and algorithm version.
     *
     * @param masterKey master key to use for obfuscation
     * @param data      data to obfuscate
     * @param version   obfuscation algorithm version to use
     *
     * @return string containing obfuscated data; use {@link #unObfuscate} to get secret data from this string
     */
    public String obfuscate(char[] masterKey, byte[] data, int version) {
        Objects.requireNonNull(masterKey);
        Objects.requireNonNull(data);
        switch (version) {
            case 1:
                return v1Obfuscator.obfuscate(masterKey, data).toString();
            default:
                throw new IllegalArgumentException("Unsupported version: " + version);
        }
    }

    /**
     * Un-obfuscate string generated with {@link #obfuscate} method.
     *
     * @param masterKey        master key to use for un-obfuscation; must match the key used for obfuscation
     * @param obfuscatedString obfuscated string generated using one of {@link #obfuscate} methods
     *
     * @return original, un-obfuscated data
     */
    public byte[] unObfuscate(char[] masterKey, String obfuscatedString) {
        Objects.requireNonNull(masterKey);
        Objects.requireNonNull(obfuscatedString);
        ObfuscatedData ob = ObfuscatedData.fromString(obfuscatedString);
        switch (ob.getVersion()) {
            case 1:
                return v1Obfuscator.unObfuscate(masterKey, ob);
            default:
                throw new IllegalArgumentException("Unsupported version: " + ob.getVersion());
        }
    }

}
