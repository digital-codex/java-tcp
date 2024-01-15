CC=clang
CFLAGS=-fPIC -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I$(PWD)/tuntap/target/include

SRCS_DIR=./tuntap/src/main/c
OBJS_DIR=./tuntap/target/objects
DEPS_DIR=./tuntap/target/include

LIBS_DIR=./lib

$(OBJS_DIR)/tuntap.o: $(SRCS_DIR)/tuntap.c $(DEPS_DIR)/dev_codex_io_TunTap.h
		$(CC) $(CFLAGS) -c $(SRCS_DIR)/tuntap.c -o $(OBJS_DIR)/tuntap.o

libtuntap.so: $(OBJS_DIR)/tuntap.o
		$(CC) $(OBJS_DIR)/tuntap.o -shared -o $(LIBS_DIR)/libtuntap.so

clean:
		rm -rf ./lib ./tuntap/target
