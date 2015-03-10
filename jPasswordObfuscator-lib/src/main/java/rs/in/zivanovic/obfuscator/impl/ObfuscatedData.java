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

import org.bouncycastle.util.encoders.Base64;

/**
 * Container for obfuscated data.
 *
 * @author Marko Zivanovic <marko@zivanovic.in.rs>
 */
public class ObfuscatedData {

    private static final String SIGNATURE = "rizobf";
    public final int version;
    public final byte[] salt;
    public final byte[] cipherText;

    public ObfuscatedData(int version, byte[] salt, byte[] cipherText) {
        this.version = version;
        this.salt = salt;
        this.cipherText = cipherText;
    }

    @Override
    public String toString() {
        return String.format("$%s$%d$%s$%s", SIGNATURE, version, Base64.toBase64String(salt),
                Base64.toBase64String(cipherText));
    }

    public static ObfuscatedData fromString(String obfuscatedString) {
        String[] parts = obfuscatedString.split("\\$");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid obfuscated data");
        }
        if (!parts[1].equals(SIGNATURE)) {
            throw new IllegalArgumentException("Invalid obfuscated data");
        }
        return new ObfuscatedData(Integer.parseInt(parts[2]), Base64.decode(parts[3]), Base64.decode(parts[4]));
    }

}