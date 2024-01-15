#!/usr/bin/env bash

make clean

tuntap_mkdir="mkdir ./lib; mkdir -p ./tuntap/target/classes; mkdir -p ./tuntap/target/include; mkdir -p ./tuntap/target/objects"
echo "$tuntap_mkdir"
if ! eval "$tuntap_mkdir"; then
  echo "Error running mkdir for tuntap..."
  exit 1
fi

tuntap_javac="javac -h ./tuntap/target/include -d ./tuntap/target/classes --add-modules tuntap ./tuntap/src/main/java/dev/codex/io/TunTap.java ./tuntap/src/main/java/module-info.java"
echo "$tuntap_javac"
if ! eval "$tuntap_javac"; then
  echo "Error running javac tuntap..."
  exit 1
fi

if ! make libtuntap.so; then
  echo "Error running make for tuntap..."
  exit 1
fi

tuntap_jar="jar cf ./tuntap/target/tuntap.jar ./tuntap/target/*"
echo "$tuntap_jar"
if ! eval "$tuntap_jar"; then
  echo "Error creating tuntap.jar..."
  exit 1
fi

tcp_java_rm="rm -rf ./tcp-java/target"
echo "$tcp_java_rm"
if ! eval "$tcp_java_rm"; then
  echo "Error rm target for tcp-java..."
  exit 1
fi

tcp_java_mkdir="mkdir -p ./tcp-java/target/classes"
echo "$tcp_java_mkdir"
if ! eval "$tcp_java_mkdir"; then
  echo "Error running mkdir for tcp-java..."
  exit 1
fi

tcp_java_javac="javac -d ./tcp-java/target/classes -classpath ./tuntap/target/classes:./tuntap/target/tuntap.jar --add-modules tuntap,tcp -p ./tuntap/target/classes:./tcp-java/target/classes ./tcp-java/src/main/java/dev/codex/tcp/Main.java ./tcp-java/src/main/java/module-info.java"
echo "$tcp_java_javac"
if ! eval "$tcp_java_javac"; then
  echo "Error running javac for tcp-java..."
  exit 1
fi

sudo /usr/local/java/jdk-17.0.2/bin/java --add-opens java.base/java.io=tuntap,tcp -Dfile.encoding=UTF-8 -classpath ./tcp-java/target/classes:./tuntap/target/classes:./tuntap/target/tuntap.jar --add-modules tuntap,tcp -p ./tuntap/target/classes:./tcp-java/target/classes dev.codex.tcp.Main
