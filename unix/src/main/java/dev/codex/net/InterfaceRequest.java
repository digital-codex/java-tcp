package dev.codex.net;

public class InterfaceRequest implements AutoCloseable {

    static {
        System.loadLibrary("interface");
    }

    private final long address;

    public InterfaceRequest() {
        this.address = this.allocate();
    }

    private native long allocate();

    public native byte[] getName();
    public native void setName(byte[] name);

    public native short getFlags();
    public native void setFlags(short flag);

    public long getAddress() {
        return this.address;
    }

    @Override
    public void close() {
        this.free();
    }

    private native void free();
}