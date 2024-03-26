package dev.codex.java.device.network;

import dev.codex.java.wrapper.runtime.*;
import dev.codex.java.wrapper.type.Error;

import java.nio.charset.Charset;

import static dev.codex.java.wrapper.runtime.NetworkTunnelInterfaceFlag.NO_PACKET_INFORMATION;

public class NetworkTunnel implements AutoCloseable {
    private final FileDescriptor file;
    private final String name;
    private final NetworkTunnelDeviceFlag device;

    public NetworkTunnel(String name, NetworkTunnelDeviceFlag device) throws Error {
        this(name, device, true);
    }

    public NetworkTunnel(String name, NetworkTunnelDeviceFlag device, boolean packet_info) throws Error {
        try (InterfaceRequest ifr = CRuntimeWrapper.malloc(InterfaceRequest.class)) {
            this.file = CRuntimeWrapper.open("/dev/net/tun", AccessFlag.READ_WRITE);
            this.name = name;
            this.device = device;
            ifr.setName(name.getBytes(Charset.defaultCharset()));
            ifr.addFlag(device);
            if (packet_info) {
                ifr.addFlag(NO_PACKET_INFORMATION);
            }
            CRuntimeWrapper.ioctl(this.file, RequestCode.SET_INTERFACE, ifr);
        }
    }

    public long transmit(byte[] bytes) {
        return CRuntimeWrapper.write(this.file, bytes, bytes.length);
    }

    public long receive(byte[] bytes) {
        return CRuntimeWrapper.read(this.file, bytes, bytes.length);
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
        CRuntimeWrapper.close(this.file);
    }

    public static void main(String[] args) {
        try (NetworkTunnel nic = new NetworkTunnel("tun0", NetworkTunnelDeviceFlag.NETWORK_TUNNEL)) {
            byte[] buf = new byte[1504];
            long read = nic.receive(buf);
            System.out.printf("Read %d bytes: %s", read, new String(buf));
        } catch (Error e) {
            e.printStackTrace(System.out);
        }
    }
}