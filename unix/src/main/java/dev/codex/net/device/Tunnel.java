package dev.codex.net.device;

import dev.codex.system.LibCWrapper;

import java.nio.charset.Charset;

public class Tunnel {
    public static void main(String[] args) {
        byte[] name = "Hello, World!".getBytes(Charset.defaultCharset());
        LibCWrapper.ioctl(0, name, 0);
        System.out.println(new String(name));
    }
}