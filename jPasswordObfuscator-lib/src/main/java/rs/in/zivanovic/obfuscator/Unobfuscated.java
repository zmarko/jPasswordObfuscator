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
import java.util.Arrays;
import java.util.Objects;
import rs.in.zivanovic.obfuscator.impl.ObfuscatedData;
import rs.in.zivanovic.obfuscator.impl.ObfuscatorException;
import rs.in.zivanovic.obfuscator.impl.V1ObfuscatorImpl;

/**
 * Wrapper around obfuscated data that performs un-obfuscation and generates output in various formats. For the sake of
 * security, you should zero your secret key data as soon as possible after constructing Unobfuscated object. Objects of
 * this class only retain un-obfuscated data after being constructed. First call to {@link #asByteArray()} or
 * {@link #asString()} methods will return copy of and clear the un-obfuscated data; further calls will throw
 * {@link ObfuscatorException}.
 */
public class Unobfuscated {

    private byte[] data;

    /**
     * Build new wrapper around obfuscated data.
     *
     * @param key master key to use for un-obfuscation
     * @param obfuscatedData obfuscated data to wrap
     */
    public Unobfuscated(char[] key, String obfuscatedData) {
        ObfuscatedData od = ObfuscatedData.fromString(obfuscatedData);
        switch (od.getVersion()) {
            case 1:
                this.data = new V1ObfuscatorImpl().unObfuscate(key, od);
                break;
            default:
                throw new IllegalArgumentException("Unsupported version: " + od.getVersion());
        }
    }

    /**
     * Return un-obfuscated data as byte array.
     *
     * @return un-obfuscated data
     */
    public byte[] asByteArray() {
        Objects.requireNonNull(data);
        byte[] ret = Arrays.copyOf(data, data.length);
        Arrays.fill(data, (byte) 0);
        data = null;
        return ret;
    }

    /**
     * Return un-obfuscated data as string.
     *
     * @return un-obfuscated data
     */
    public String asString() {
        return new String(asByteArray(), StandardCharsets.UTF_8);
    }

}
