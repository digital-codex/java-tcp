package dev.codex.java.device.network;

import dev.codex.java.NativeRuntimeWrapperAnchor;
import dev.codex.java.wrapper.library.NativeLibraryLoader;
import dev.codex.java.wrapper.runtime.*;
import dev.codex.java.wrapper.type.Error;

import java.nio.charset.Charset;

import static dev.codex.java.wrapper.runtime.NetworkTunnelInterfaceFlag.NO_PACKET_INFORMATION;

public class NetworkTunnel implements AutoCloseable {
    static {
        NativeLibraryLoader.load(NativeRuntimeWrapperAnchor.class, "libNativeRuntimeWrapper.so");
    }

    private final FileDescriptor file;
    private final String name;
    private final NetworkTunnelDeviceFlag device;

    public NetworkTunnel(String name, NetworkTunnelDeviceFlag device) throws Error {
        this(name, device, true);
    }

    public NetworkTunnel(String name, NetworkTunnelDeviceFlag device, boolean packet_info) throws Error {
        try (InterfaceRequest ifr = NativeRuntimeWrapper.malloc(InterfaceRequest.class)) {
            this.file = NativeRuntimeWrapper.open("/dev/net/tun", AccessFlag.READ_WRITE);
            this.name = name;
            this.device = device;
            ifr.setName(name.getBytes(Charset.defaultCharset()));
            ifr.addFlag(device);
            if (packet_info) {
                ifr.addFlag(NO_PACKET_INFORMATION);
            }
            NativeRuntimeWrapper.ioctl(this.file, RequestCode.SET_INTERFACE, ifr);
        }
    }

    public long transmit(byte[] bytes) {
        return NativeRuntimeWrapper.write(this.file, bytes, bytes.length);
    }

    public long receive(byte[] bytes) {
        return NativeRuntimeWrapper.read(this.file, bytes, bytes.length);
    }

    public FileDescriptor fd() {
        return this.file;
    }

    public String name() {
        return this.name;
    }

    public NetworkTunnelDeviceFlag device() {
        return this.device;
    }

    @Override
    public void close() {
        NativeRuntimeWrapper.close(this.file);
    }

    public static void main(String[] args) {
        NativeRuntimeWrapper.println("Hello, World");
    }
}