#!/bin/sh
# macOS launcher — double-click to play. Uses the bundled runtime; no Java install needed.
cd "$(dirname "$0")" || exit 1
if [ "$(uname -m)" = "arm64" ]; then
  RT="runtime/macos-aarch64"
else
  RT="runtime/macos-x64"
fi
"$RT/bin/java" -cp bin Main
