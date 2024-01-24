package dev.codex.io;

public enum AccessMode {
    READ_ONLY(0x00000000),
    WRITE_ONLY(0x00000001),
    READ_WRITE(0x00000002);

    private final int flag;

    AccessMode(int flag) {
        this.flag = flag;
    }

    public int flag() {
        return this.flag;
    }
}