package dev.codex.tcp;

import dev.codex.io.TunTap;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        TunTap tun = new TunTap("tun0", TunTap.Mode.TUN);
        System.out.println(tun.name());
        byte[] buf = new byte[1504];
        int read = tun.recv(buf);
        System.out.printf("read %d bytes%n", read);
    }
}