package dev.digitalcodex.io;

import java.io.File;

public class TunTap {
    public static final String libPath = System.getProperty("user.dir") + File.separatorChar + "tuntap" + File.separatorChar + "lib" + File.separatorChar + "libtuntap.so";

    static {
        System.load(TunTap.libPath);
    }

    public native int tuntap_setup(int fc, String name, int mode, int packet_info);

    public static void main(String[] args) {
        new TunTap().tuntap_setup(0, "Goodbye, Moon.", 0, 0);
    }
}