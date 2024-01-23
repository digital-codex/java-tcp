CC=clang
CFLAGS=-fPIC -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux -I$(PWD)/unix/target/include

SRCS_DIR=./src/main/c

OBJS_DIR=./bin
DEPS_DIR=./target/include
LIBS_DIR=./target/library

$(OBJS_DIR)/cwrapper.o: $(SRCS_DIR)/cwrapper.c $(DEPS_DIR)/dev_codex_system_LibCWrapper.h
		$(CC) $(CFLAGS) -c $(SRCS_DIR)/cwrapper.c -o $(OBJS_DIR)/cwrapper.o

libcwrapper.so: $(OBJS_DIR)/cwrapper.o
		$(CC) $(OBJS_DIR)/cwrapper.o -shared -o $(LIBS_DIR)/libcwrapper.so

clean:
		rm -rf ./bin