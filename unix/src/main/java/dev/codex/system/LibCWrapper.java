package dev.codex.system;

public class LibCWrapper {
    static {
        System.loadLibrary("cwrapper");
    }

    public static native int open(String path, int flags);

    public static native int close(int fd);

    public static final String TUN_PATH = "/dev/net/tun";

    //TODO: should recreate this in java
    public static native long TUNSETIFF();

    public static native int ioctl(int fd, long request, long arg);
}