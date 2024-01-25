package dev.codex.net.device;

import dev.codex.io.AccessMode;
import dev.codex.io.FileStream;
import dev.codex.net.InterfaceRequest;
import dev.codex.system.LibCWrapper;

import java.io.IOException;
import java.nio.charset.Charset;

public class InterfaceTunnel implements AutoCloseable {

    static {
        System.loadLibrary("cwrapper");
    }

    private final FileStream file;
    private final String name;
    private final RequestMode mode;

    public InterfaceTunnel(String name, RequestMode mode) throws IOException {
        this(name, mode, true);
    }

    public InterfaceTunnel(String name, RequestMode mode, boolean packet_info) throws IOException {
        try (InterfaceRequest ifr = new InterfaceRequest()) {
            this.file = new FileStream(LibCWrapper.TUN_PATH, AccessMode.READ_WRITE).open();
            this.mode = mode;
            ifr.setName(name.getBytes(Charset.defaultCharset()));
            ifr.setFlags(mode.flag());
            if (packet_info) {
                ifr.setFlags(RequestFlags.IFF_NO_PI);
            }
            if (LibCWrapper.ioctl(this.file.fd(), LibCWrapper.TUNSETIFF(), ifr.getAddress()) < 0) {
                throw new IOException("Exception occurred while setting up interface tunnel " + LibCWrapper.strerror());
            }

            this.name = new String(ifr.getName());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    public FileStream fd() {
        return this.file;
    }

    public String name() {
        return this.name;
    }

    public RequestMode mode() {
        return this.mode;
    }

    public static void main(String[] args) throws Exception {
        FileStream fs = new FileStream("/home/treyvon/src/tcp/unix/src/main/resources/test.txt", AccessMode.READ_WRITE).open();
        fs.write(new byte[]{'H', 'e', 'l', 'l', 'o', ',', ' ', 'W', 'o', 'r', 'l', 'd'});
        fs.close();
    }

    @Override
    public void close() throws Exception {
        this.file.close();
    }
}