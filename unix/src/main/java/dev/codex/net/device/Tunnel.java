package dev.codex.net.device;

import dev.codex.system.LibCWrapper;

public class Tunnel {
    public static void main(String[] args) {
        LibCWrapper.ioctl(0, 0, 0);
    }
}