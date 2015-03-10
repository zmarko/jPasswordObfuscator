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

/**
 * Command line interface for obfuscating and de-obfuscating data.
 *
 * @author Marko Zivanovic <marko@zivanovic.in.rs>
 */
public class Run {

    public static void main(String[] args) {
        if (args.length != 3) {
            showHelp();
        } else {
            switch (args[0]) {
                case "o":
                    obfuscate(args);
                    break;
                case "d":
                    deObfuscate(args);
                    break;
                default:
                    showHelp();
            }
        }
    }

    private static void showHelp() {
        System.out.println(String.format("Data obfuscation / de-obfuscation tool"));
        System.out.
                println(String.format("Syntax: java -jar <file.jar> <operation> <master key> <data to de/obfuscate>"));
        System.out.println(String.format("  <operation> - 'o' - obfuscate, 'd' - de-obfuscate"));

    }

    private static void deObfuscate(String[] args) {
        char[] masterKey = args[1].toCharArray();
        String data = args[2];
        System.out.println(new String(new JPasswordObfuscator().deObfuscate(masterKey, data), StandardCharsets.UTF_8));
    }

    private static void obfuscate(String[] args) {
        char[] masterKey = args[1].toCharArray();
        byte[] data = args[2].getBytes(StandardCharsets.UTF_8);
        System.out.println(new JPasswordObfuscator().obfuscate(masterKey, data));
    }

}
