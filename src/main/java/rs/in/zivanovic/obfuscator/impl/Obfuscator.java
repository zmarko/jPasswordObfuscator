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

/**
 * Obfuscator/de-obfuscator interface.
 *
 * @author Marko Zivanovic <marko@zivanovic.in.rs>
 */
public interface Obfuscator {

    /**
     * Obfuscate sensitive data using supplied master key.
     *
     * @param masterKey master key to use for data obfuscation
     * @param data      sensitive data to obfuscate
     *
     * @return obfuscated string
     */
    ObfuscatedData obfuscate(char[] masterKey, byte[] data);

    /**
     * De-obfuscate data using supplied master key.
     *
     * @param masterKey master key to use for data de-obfuscation; must match master key used for obfuscation
     * @param data      string obfuscated with {@link #obfuscate} to de-obfuscate
     *
     * @return
     */
    byte[] deObfuscate(char[] masterKey, ObfuscatedData data);
}
