CC=clang
CFLAGS=-fPIC -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -Wall

SRCS_DIR=./src/main/c
DEPS_DIR=./include

LIBS_DIR=./lib

$(SRCS_DIR)/tuntap.o: $(SRCS_DIR)/tuntap.c $(DEPS_DIR)/dev_digitalcodex_io_TunTap.h
		$(CC) -c $(CFLAGS) $(SRCS_DIR)/tuntap.c -o $(SRCS_DIR)/tuntap.o

libtuntap.so: $(SRCS_DIR)/tuntap.o
		$(CC) $(SRCS_DIR)/tuntap.o -shared -o $(LIBS_DIR)/libtuntap.so

clean:
		rm -f $(LIBS_DIR)/libtuntap.so $(SRCS_DIR)/tuntap.o ./src/main/java/dev/digitalcodex/io/TunTap.class
