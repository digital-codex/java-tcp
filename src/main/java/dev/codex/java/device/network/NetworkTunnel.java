package dev.codex.java.device.network;

import dev.codex.java.wrapper.runtime.*;
import dev.codex.java.wrapper.runtime.InterfaceRequest;
import dev.codex.java.wrapper.type.Error;

import java.io.IOException;
import java.nio.charset.Charset;

import static dev.codex.java.wrapper.runtime.NetworkTunnelRequestFlag.*;

public class NetworkTunnel implements AutoCloseable {
    private final FileDescriptor file;
    private final String name;
    private final RequestMode mode;

    public NetworkTunnel(String name, RequestMode mode) throws IOException {
        this(name, mode, true);
    }

    public NetworkTunnel(String name, RequestMode mode, boolean packet_info) throws IOException {
        try (dev.codex.java.wrapper.runtime.InterfaceRequest ifr = CRuntimeWrapper.malloc(InterfaceRequest.class)) {
            this.file = CRuntimeWrapper.open("/dev/net/tun", AccessFlag.READ_WRITE);
            this.name = name;
            this.mode = mode;
            ifr.setName(name.getBytes(Charset.defaultCharset()));
            ifr.addFlag(mode);
            if (packet_info) {
                ifr.addFlag(NO_PACKET_INFORMATION);
            }
            CRuntimeWrapper.ioctl(this.file, RequestCode.TUNSETIFF, ifr);
        } catch (Error e) {
            throw new IOException("Failed to initialize network tunnel");
        }
    }

    public long send(byte[] bytes) throws IOException {
        return CRuntimeWrapper.write(this.file, bytes, bytes.length);
    }

    public long receive(byte[] bytes) throws IOException {
        return CRuntimeWrapper.read(this.file, bytes, bytes.length);
    }

    public FileDescriptor fd() {
        return this.file;
    }

    public String name() {
        return this.name;
    }

    public RequestMode mode() {
        return this.mode;
    }

    @Override
    public void close() {
        CRuntimeWrapper.close(this.file);
    }

    public static void main(String[] args) {
        try (NetworkTunnel nic = new NetworkTunnel("tun0", RequestMode.TUNNEL)) {
            byte[] buf = new byte[1504];
            long read = nic.receive(buf);
            System.out.printf("Read %d bytes: %s", read, new String(buf));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}