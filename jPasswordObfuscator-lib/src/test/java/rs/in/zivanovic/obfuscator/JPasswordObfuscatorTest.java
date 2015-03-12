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
 * Obfuscator unit tests.
 */
public class JPasswordObfuscatorTest {

    private static final char[] masterKey = "masterKey".toCharArray();
    private static final char[] masterKey1 = "masterKey1".toCharArray();
    private static final char[] masterKey2 = "masterKey2".toCharArray();
    private static final byte[] data = "data".getBytes(StandardCharsets.UTF_8);
    private final JPasswordObfuscator engine = new JPasswordObfuscator();

    @Test
    public void testObfuscateUnObfuscate() {
        String ob = engine.obfuscate(masterKey, data);
        System.out.println(ob);
        byte[] unob = engine.unObfuscate(masterKey, ob);
        assertArrayEquals(data, unob);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObfuscateInvalidVersion() {
        String ob = engine.obfuscate(masterKey, data, 123);
    }

    @Test(expected = RuntimeException.class)
    public void testObfuscateUnObfuscateWrongMasterKey() {
        String ob = engine.obfuscate(masterKey1, data);
        byte[] unob = engine.unObfuscate(masterKey2, ob);
        assertThat(data, not(equalTo(unob)));
    }

    @Test
    public void testObfuscateTiming() {
        int timing_length = 3;
        long start = System.currentTimeMillis();
        long count = 0;
        while (System.currentTimeMillis() - start < timing_length * 1_000) {
            count++;
            engine.obfuscate(masterKey, data);
        }
        System.out.println(String.format("%d iterations in %d seconds, %.2f iterations per second", count,
                timing_length, (float) count / timing_length));
    }

    @Test(expected = NullPointerException.class)
    public void testObfuscateNullData() {
        engine.obfuscate(masterKey, null);
    }

    @Test(expected = NullPointerException.class)
    public void testObfuscateNullMasterKey() {
        engine.obfuscate(null, data);
    }

    @Test(expected = NullPointerException.class)
    public void testUnobfuscateNullData() {
        engine.unObfuscate(masterKey, null);
    }

    @Test(expected = RuntimeException.class)
    public void testUnobfuscateGarbageData() {
        engine.unObfuscate(masterKey, "23499999puchn-p23948hfrn-[8f2333331xd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnobfuscateInvalidSignature() {
        engine.unObfuscate(masterKey, "$INV$1$v1hACmQULBk=$ggRxqVe1fUMvUnzpLA6CoQ==");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnobfuscateTruncated() {
        engine.unObfuscate(masterKey, "$rizobf$1$v1hACmQULBk=$");
    }

    @Test(expected = RuntimeException.class)
    public void testUnobfuscateInvalidData() {
        engine.unObfuscate(masterKey, "$rizobf$1$v1hACmQULBk=$ggRxqVe1fUMvUnzpLA6Coq");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnobfuscateInvalidVersion() {
        engine.unObfuscate(masterKey, "$rizobf$211$v1hACmQULBk=$ggRxqVe1fUMvUnzpLA6Coq");
    }

}
