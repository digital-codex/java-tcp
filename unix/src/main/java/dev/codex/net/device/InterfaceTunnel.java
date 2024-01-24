package dev.codex.net.device;

import dev.codex.io.AccessMode;
import dev.codex.io.UnixFileStream;
import dev.codex.net.InterfaceRequest;
import dev.codex.system.LibCWrapper;

import java.io.IOException;
import java.nio.charset.Charset;

public class InterfaceTunnel {

    static {
        System.loadLibrary("cwrapper");
    }

    private final UnixFileStream fd;
    private final String name;
    private final RequestMode mode;

    public InterfaceTunnel(String name, RequestMode mode) throws IOException {
        this(name, mode, true);
    }

    public InterfaceTunnel(String name, RequestMode mode, boolean packet_info) throws IOException {
        try (InterfaceRequest ifr = new InterfaceRequest()) {
            this.fd = new UnixFileStream(LibCWrapper.TUN_PATH, AccessMode.READ_WRITE).open();
            ifr.setName(name.getBytes(Charset.defaultCharset()));
            ifr.setFlags(mode.flag());
            if (packet_info) {
                ifr.setFlags(RequestFlags.IFF_NO_PI);
            }
            int err;
            if ((err = LibCWrapper.ioctl(this.fd.getFd(), LibCWrapper.TUNSETIFF(), ifr.getAddress())) < 0) {
                throw new IOException("Error in ioctl: " + err);
            }

            this.name = new String(ifr.getName());
            this.mode = mode;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public UnixFileStream fd() {
        return this.fd;
    }

    public String name() {
        return this.name;
    }

    public RequestMode mode() {
        return this.mode;
    }

    public static void main(String[] args) {
        InterfaceRequest ifr = new InterfaceRequest();
        ifr.setName("Hello, World!".getBytes(Charset.defaultCharset()));
        ifr.setFlags(RequestMode.TUN.flag());
        ifr.setFlags(RequestFlags.IFF_NO_PI);
        System.out.println(new String(ifr.getName()).trim());
        System.out.println(ifr.getFlags());
        ifr.close();
    }
}