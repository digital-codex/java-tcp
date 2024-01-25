package dev.codex.io;

public enum AccessMode {
    READ_ONLY(0x00000000) {
        @Override
        public boolean canRead() {
            return true;
        }
    },
    WRITE_ONLY(0x00000001) {
        @Override
        public boolean canWrite() {
            return true;
        }
    },
    READ_WRITE(0x00000002) {
        @Override
        public boolean canRead() {
            return true;
        }

        @Override
        public boolean canWrite() {
            return true;
        }
    };

    private final int flag;

    AccessMode(int flag) {
        this.flag = flag;
    }

    public boolean canRead() {
        return false;
    }

    public boolean canWrite() {
        return false;
    }

    public int flag() {
        return this.flag;
    }
}