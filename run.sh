#!/usr/bin/env bash

compile="mvn clean compile"
echo "$compile"
if ! eval "$compile"; then
  echo "Error compiling java..."
  exit 1
fi

run="java -Djava.library.path=/home/treyvon/src/tcp/unix/target/library -Dfile.encoding=UTF-8 -classpath /home/treyvon/src/tcp/unix/target/classes dev.codex.net.device.InterfaceTunnel"
echo "$run"
if ! eval "$run"; then
  echo "Error running java..."
  exit 1
fi