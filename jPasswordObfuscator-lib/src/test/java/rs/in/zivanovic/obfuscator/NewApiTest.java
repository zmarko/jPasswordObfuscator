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
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * New API ({@link Obfuscated} and {@link Unobfuscated}) tests.
 */
public class NewApiTest {

    private static final char[] key = "key".toCharArray();
    private static final String dataS = "data";
    private static final byte[] dataBA = dataS.getBytes(StandardCharsets.UTF_8);

    @Test
    public void testObfuscateUnobfuscateArrays() {
        String o = new Obfuscated(key, dataBA).toString();
        byte[] unob = new Unobfuscated(key, o).asByteArray();
        assertThat(unob, equalTo(dataBA));
    }

    @Test
    public void testObfuscateUnobfuscateStrings() {
        String o = new Obfuscated(key, dataS).toString();
        String unob = new Unobfuscated(key, o).asString();
        assertThat(unob, equalTo(dataS));
    }

    @Test(expected = NullPointerException.class)
    public void testUnobfuscationClearsData() {
        String o = new Obfuscated(key, dataBA).toString();
        Unobfuscated unob = new Unobfuscated(key, o);
        unob.asByteArray();
        unob.asByteArray();
    }

}
