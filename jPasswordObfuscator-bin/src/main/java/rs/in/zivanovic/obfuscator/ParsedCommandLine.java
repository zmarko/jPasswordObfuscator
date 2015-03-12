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

import com.beust.jcommander.JCommander;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Wrapper around command line arguments that performs parsing and returns callable that executes required command.
 */
public class ParsedCommandLine {

    private static final Map<String, Callable<String>> COMMANDS = new HashMap<>();
    private static final Map<String, String[]> ALIASES = new HashMap<>();
    private final Callable<String> command;
    private final StringBuilder usage = new StringBuilder();

    static {
        COMMANDS.put("o", new ObfuscateCommand());
        ALIASES.put("o", new String[]{"ob", "obfuscate"});
        COMMANDS.put("u", new UnobfuscateCommand());
        ALIASES.put("u", new String[]{"unob", "unobfuscate"});
    }

    /**
     * Parse command line arguments and prepare command to execute and usage help text.
     *
     * @param args command line arguments to parse
     */
    public ParsedCommandLine(String[] args) {
        JCommander jc = new JCommander();
        addCommands(jc);
        jc.usage(usage);
        this.command = parse(jc, args);
    }

    /**
     * Get the command to execute.
     *
     * @return command to execute
     */
    public Callable<String> getCommand() {
        return command;
    }

    /**
     * Get usage help text.
     *
     * @return usage help text
     */
    public String getHelpText() {
        return usage.toString();
    }

    private void addCommands(JCommander jc) {
        for (Map.Entry<String, Callable<String>> e : COMMANDS.entrySet()) {
            String[] a = ALIASES.get(e.getKey());
            jc.addCommand(e.getKey(), e.getValue(), a);
        }
    }

    private Callable<String> parse(JCommander jc, String[] args) {
        Callable ret = null;
        jc.parse(args);
        if (COMMANDS.keySet().contains(jc.getParsedCommand())) {
            ret = COMMANDS.get(jc.getParsedCommand());
        }
        return ret;
    }

}
