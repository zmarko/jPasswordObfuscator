[![Build Status](https://travis-ci.org/zmarko/jPasswordObfuscator.svg)](https://travis-ci.org/zmarko/jPasswordObfuscator)

# jPasswordObfuscator
Extensible Java library and command line utility for sensitive data obfuscation and de-obfuscation. Primarily designed
to protect sensitive data in configuration files (passwords, ...) and similar scenarios.

At it's core, this library uses strong cryptographic algorithms to encrypt / decrypt data using user-supplied secret key.
Since data and the key are kept closely together (key in the software, data in the configuration files) this method does 
not provide any security against determined attacker, only a way to hide (obfuscate) the data from casual onlookers.

Maven dependency is available at:

    <dependency>
        <groupId>rs.in.zivanovic</groupId>
        <artifactId>j-password-obfuscator</artifactId>
        <version>1.0.0</version>
    </dependency>

## Typical usage

### Obfuscation

Say we want to obfuscate our super secret data string "Hello World" using the secret key "key".
We'd use the following snippet:

    JPasswordObfuscator obfuscator = new JPasswordObfuscator();
    String obfuscated = obfuscator.obfuscate("key".toCharArray(), "Hello World".getBytes(StandardCharsets.UTF_8));
    System.out.println(obfuscated);

output will resemble this:

    $rizobf$1$l7IwmuwEZnY=$F5K7LeIP0u1cSluV3wBXqQ==

Note that your output will be different. Obfuscation algorithm introduces random salt into the data, so, every 
invocation of the obfuscation method, even with the same inputs will generate different output.

As with all secret keys, it is recommended to use random, or, at least, not easily guessed data here.

### De-obfuscation

When we want to un-obfuscate data:

    JPasswordObfuscator obfuscator = new JPasswordObfuscator();
    String original = obfuscator.deObfuscate("key".toCharArray(), "$rizobf$1$l7IwmuwEZnY=$F5K7LeIP0u1cSluV3wBXqQ==");
    System.out.println(original);

and we get our original super secret data back:

    Hello World
