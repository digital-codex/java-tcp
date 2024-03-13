package dev.codex.net.device;

public enum RequestMode {
    TUN(0x0001),
    TAP(0x0002);

    private final short flag;

    RequestMode(int flag) {
        this.flag = (short) flag;
    }

    public short flag() {
        return this.flag;
    }
}