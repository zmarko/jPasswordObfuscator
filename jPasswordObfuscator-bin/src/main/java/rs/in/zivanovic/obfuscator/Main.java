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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Main entry point.
 *
 * @author Marko Zivanovic <marko@zivanovic.in.rs>
 */
public class Main {

    public static void main(String args[]) {
        JCommander jc = new JCommander();
        Map<String, Runnable> commands = new HashMap<>();
        Map<String, String[]> aliases = new HashMap<>();
        commands.put("o", new ObfuscateCommand());
        aliases.put("o", new String[]{"ob", "obfuscate"});
        commands.put("d", new DeObfuscateCommand());
        aliases.put("d", new String[]{"deob", "deobfuscate"});
        for (Entry<String, Runnable> e : commands.entrySet()) {
            String[] a = aliases.get(e.getKey());
            jc.addCommand(e.getKey(), e.getValue(), a);
        }

        try {
            jc.parse(args);
            if (commands.keySet().contains(jc.getParsedCommand())) {
                Runnable r = commands.get(jc.getParsedCommand());
                r.run();
            } else {
                jc.usage();
            }
        } catch (RuntimeException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
