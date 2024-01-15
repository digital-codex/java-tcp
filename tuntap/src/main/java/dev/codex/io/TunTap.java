package dev.codex.io;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TunTap {
    public enum Mode {
        TUN, TAP
    }

    public static final String LIB_PATH = System.getProperty("user.dir") + File.separatorChar + "lib" + File.separatorChar + "libtuntap.so";

    static {
        System.load(TunTap.LIB_PATH);
    }

    public native String tuntap_setup(int fd, String name, int mode, int packet_info);

    private static final Path PATH = Paths.get("/dev/net/tun");

    private final Mode mode;
    private final String name;

    public TunTap(String ifName, Mode mode) throws IOException {
        this(ifName, mode, true);
    }

    public TunTap(String ifName, Mode mode, boolean packetInfo) throws IOException {
        this.mode = mode;
        String name_buf = ifName + '\0';
        try (FileInputStream in = new FileInputStream(TunTap.PATH.toFile())) {
            FileDescriptor descriptor = in.getFD();
            Class<? extends FileDescriptor> clazz = descriptor.getClass();
            Field[] fields = clazz.getDeclaredFields();
            int fd = -1;
            for (Field field : fields) {
                if (field.getName().compareToIgnoreCase("fd") == 0) {
                    field.setAccessible(true);
                    fd = (int) field.get(descriptor);
                }
            }

            String result = this.tuntap_setup(fd, name_buf, mode.ordinal(), packetInfo ? 1 : 0);
            if (result == null) {
                throw new IOException("Exception occurred while setting up tuntap");
            }

            this.name = result;
        } catch (IOException | IllegalAccessException e) {
            throw new IOException("Exception occurred while setting up tuntap", e);
        }
    }

    public int recv(byte[] buf) throws IOException {
        try (FileInputStream in = new FileInputStream(TunTap.PATH.toFile())) {
            return in.read(buf);
        } catch (IOException e) {
            throw new IOException("Exception occurred while setting up tuntap", e);
        }
    }

    public void send(byte[] buf) throws IOException {
        try (FileOutputStream out = new FileOutputStream(TunTap.PATH.toFile())) {
            out.write(buf);
        } catch (IOException e) {
            throw new IOException("Exception occurred while setting up tuntap", e);
        }
    }

    public Mode mode() {
        return this.mode;
    }

    public String name() {
        return this.name;
    }
}