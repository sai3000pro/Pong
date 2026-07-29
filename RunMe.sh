#!/bin/sh
# Linux launcher — run ./RunMe.sh to play. Uses the bundled runtime; no Java install needed.
cd "$(dirname "$0")" || exit 1
"runtime/linux-x64/bin/java" -cp bin Main
