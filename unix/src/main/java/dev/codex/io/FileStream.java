package dev.codex.io;

import dev.codex.system.LibCWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileStream implements AutoCloseable {
    private String path;
    private AccessMode mode;
    private int fd;

    public FileStream(String name) throws FileNotFoundException, NullPointerException {
        this(name != null ? new File(name) : null, AccessMode.READ_ONLY);
    }

    public FileStream(String name, AccessMode mode) throws FileNotFoundException, NullPointerException {
        this(name != null ? new File(name) : null, mode);
    }

    public FileStream(File file) throws FileNotFoundException, NullPointerException {
        this(file, AccessMode.READ_ONLY);
    }

    public FileStream(File file, AccessMode mode) throws FileNotFoundException, NullPointerException {
        String path = file != null ? file.getPath() : null;
        if (path == null) {
            throw new NullPointerException();
        }
        if (!file.exists()) {
            throw new FileNotFoundException("Invalid file path");
        }

        this.path = path;
        this.mode = mode;
        this.fd = -1;
    }

    public FileStream open() {
        this.fd = LibCWrapper.open(this.path, this.mode.flag());
        return this;
    }

    public int getFd() {
        return this.fd;
    }

    @Override
    public void close() throws Exception {
        int err;
        if ((err = LibCWrapper.close(this.fd)) < 0) {
            throw new IOException("Error in close: " + err);
        }
        this.fd = -1;
    }
}