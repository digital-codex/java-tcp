package dev.codex.system;

public class LibCWrapper {
    static {
        System.loadLibrary("cwrapper");
    }

    public static native int ioctl(int fd, long request, long arg);
}