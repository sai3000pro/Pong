# Build guide

This project ships with a bundled Java runtime per OS so end users don't need Java
installed. This document explains how to regenerate the compiled classes and the
`runtime/` folders, and how to produce the per-OS release zips.

## Prerequisites
- A **JDK 21** (not just a JRE). Portable zips: https://adoptium.net/temurin/releases/?version=21
  You only need it on the *build* machine; end users need nothing.
- To cross-build the macOS/Linux runtimes from Windows, download the target-OS JDK 21
  archives too — `jlink` packages whichever platform's modules you point it at.

Set a convenience variable to the JDK's `bin` (adjust the path/version):
```sh
JDK=/path/to/jdk-21/bin        # Windows: set JDK=C:\path\to\jdk-21\bin
```

## 1. Compile the game
```sh
"$JDK/javac" src/PingPong/*.java -d bin
```
Sources have no `package` declaration, so the entry point is simply `Main` on the
classpath `bin`.

## 2. Build the runtime for the current OS
`java.desktop` pulls in Swing/AWT (`java.base` comes with it) and covers `javax.sound`:
```sh
"$JDK/jlink" --add-modules java.desktop \
  --strip-debug --no-header-files --no-man-pages --compress=2 \
  --output runtime/<platform>
```
Use these `<platform>` folder names (the launchers depend on them):
`windows-x64`, `linux-x64`, `macos-aarch64`, `macos-x64`.

## 3. Cross-build the other OSes (optional, from any host)
`jlink` is host-agnostic — give it the **target** JDK's `jmods` and it emits a runtime
for that target:
```sh
"$JDK/jlink" --module-path /path/to/<target-jdk>/jmods \
  --add-modules java.desktop \
  --strip-debug --no-header-files --no-man-pages --compress=2 \
  --output runtime/<platform>
```
(On macOS JDKs the jmods live under `Contents/Home/jmods`.)

Verify each runtime is really for its target by its native VM library:
`bin/server/jvm.dll` (Windows), `lib/server/libjvm.so` (Linux),
`lib/server/libjvm.dylib` (macOS).

## 4. Launchers
- `RunMe.bat` — Windows → `runtime\windows-x64`
- `RunMe.command` — macOS → auto-selects `macos-aarch64` (Apple Silicon) or `macos-x64` (Intel) via `uname -m`
- `RunMe.sh` — Linux → `runtime/linux-x64`

Each launcher `cd`s to its own folder first so the `.wav` files resolve, then runs
`java -cp bin Main`. Keep the executable bit on the `.command`/`.sh` files and on the
Unix runtime binaries (see below).

## 5. Build the per-OS release zips
The Unix runtimes need their binaries marked executable. Because that bit lives in the
git index (not the Windows filesystem), set it once in git, commit, then use
`git archive` — which bakes the recorded permissions into the archive:
```sh
git update-index --chmod=+x \
  runtime/linux-x64/bin/* runtime/linux-x64/lib/jexec runtime/linux-x64/lib/jspawnhelper \
  runtime/macos-aarch64/bin/* runtime/macos-aarch64/lib/jspawnhelper \
  runtime/macos-x64/bin/* runtime/macos-x64/lib/jspawnhelper \
  RunMe.command RunMe.sh
git commit -am "…"

# Each zip is a self-contained, playable bundle for one OS:
git archive --format=zip -o Pong-Windows.zip HEAD RunMe.bat bin src *.wav README.md runtime/windows-x64
git archive --format=zip -o Pong-macOS.zip   HEAD RunMe.command bin src *.wav README.md runtime/macos-aarch64 runtime/macos-x64
git archive --format=zip -o Pong-Linux.zip   HEAD RunMe.sh bin src *.wav README.md runtime/linux-x64
```
Attach the three zips to a GitHub Release:
```sh
gh release create v1.0 Pong-Windows.zip Pong-macOS.zip Pong-Linux.zip \
  --title "Pong v1.0" --notes "Download the zip for your OS, unzip, and run the launcher."
```

## Toolchain used for the current runtimes
Eclipse Temurin JDK 21 (21.0.11 for Windows/macOS, 21.0.12 for Linux), `java.desktop`
module only, `--compress=2`, debug/headers/man-pages stripped.
