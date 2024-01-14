#!/usr/bin/env bash

cd tuntap

make clean

echo "Compiling TunTap.java..."
javac -h ./include ./src/main/java/dev/digitalcodex/io/TunTap.java

echo "Building tuntap library..."
make libtuntap.so

cd ..

