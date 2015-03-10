/*
 * The MIT License
 *
 * Copyright 2015 Marko Zivanovic.
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

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.base.Joiner;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Implementation of the obfuscate command.
 *
 * @author Marko Zivanovic <marko@zivanovic.in.rs>
 */
@Parameters(commandDescription = "Obfuscate sensitive data")
public class ObfuscateCommand implements Runnable {

    @Parameter(names = {"-k", "--key"}, description = "Master key to use for obfuscation", required = true)
    private String masterKey;

    @Parameter(names = {"-v", "--version"}, description = "Version of the algorithm to use", required = false)
    @SuppressWarnings("FieldMayBeFinal")
    private int version = 1;

    @Parameter(description = "data to obfuscate", required = true)
    private List<String> data;

    @Override
    public void run() {
        JPasswordObfuscator jpo = new JPasswordObfuscator();
        byte[] dataBytes = Joiner.on(' ').join(data).getBytes(StandardCharsets.UTF_8);
        String s = jpo.obfuscate(masterKey.toCharArray(), dataBytes, version);
        System.out.println(s);
    }

}
