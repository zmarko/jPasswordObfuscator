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
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Implementation of the un-obfuscate command.
 */
@Parameters(commandDescription = "Un-obfuscate sensitive data")
public class UnobfuscateCommand implements Callable<String> {

    @Parameter(names = {"-k", "--key"}, description = "Master key to use for un-obfuscation", required = true)
    private String masterKey;

    @Parameter(description = "obfuscated string to un-obfuscate", required = true)
    private List<String> params;

    @Override
    public String call() {
        String obfuscated = Joiner.on(' ').skipNulls().join(params);
        return new Unobfuscated(masterKey.toCharArray(), obfuscated).asString();
    }

}
