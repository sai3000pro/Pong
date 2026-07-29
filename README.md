# Pong

The classic 1970s Pong game, remade in Java — first to 5 points wins!
Originally built for a school ICS4U assignment, then packaged so **anyone can play it with a single double-click, no Java install required.**

[![Smoke test bundled runtimes](https://github.com/sai3000pro/Pong/actions/workflows/smoke-test.yml/badge.svg)](https://github.com/sai3000pro/Pong/actions/workflows/smoke-test.yml)
[![Latest release](https://img.shields.io/github/v/release/sai3000pro/Pong?label=play%20now)](https://github.com/sai3000pro/Pong/releases/latest)

---

## ▶️ How to run

**No Java installation required** — a trimmed Java runtime ships with the game for every OS.

**Easiest — download & play:** grab the zip for your operating system from the
[**latest release**](https://github.com/sai3000pro/Pong/releases/latest), unzip it, and run the launcher inside:

| OS | Download | Launch |
|----|----------|--------|
| **Windows** | `Pong-Windows.zip` | double-click **`RunMe.bat`** |
| **macOS** (Apple Silicon *or* Intel) | `Pong-macOS.zip` | double-click **`RunMe.command`** |
| **Linux** | `Pong-Linux.zip` | run **`./RunMe.sh`** |

**Or run straight from the repo:** clone it (or **Code → Download ZIP**) and use the same launchers above — they live in the project root.

That's it — the game window opens. No install, no internet, no setup.

> **macOS note:** the bundled runtime is unsigned, so Gatekeeper may block it on first launch (especially if you downloaded the ZIP via a browser, which "quarantines" it). The reliable fix is to open **Terminal** in the project folder and run once:
> ```sh
> xattr -dr com.apple.quarantine .
> chmod +x RunMe.command
> ```
> Then double-click `RunMe.command` (or run `./RunMe.command`). Cloning with `git` instead of downloading the ZIP usually avoids the quarantine entirely.

---

## 🎮 Controls

Press `space` to start the game.

| | Move up | Move down |
|--|--|--|
| **Player 1** | `w` | `s` |
| **Player 2** | `↑` | `↓` |

---

## ✨ Zero-dependency packaging — how it works

A Java program normally needs a JVM installed on the player's machine. To make this game "just work" on
a clean computer, a **minimal Java runtime travels with the project** and each launcher runs the game
against it directly.

- **Trimmed runtimes via `jlink`.** Each `runtime/<os>/` folder is a custom Java 21 image built with
  `jlink`, containing only the `java.desktop` module the game needs (Swing + `javax.sound`). That keeps
  every runtime to ~45–58 MB instead of shipping a full ~300 MB JDK.
- **All four platforms, built from one machine.** `jlink` is host-agnostic — pointed at another
  platform's modules it emits that platform's runtime — so `windows-x64`, `linux-x64`,
  `macos-aarch64`, and `macos-x64` are all produced from a single build.
- **Thin per-OS launchers.** `RunMe.bat` (Windows), `RunMe.command` (macOS, auto-selecting Apple Silicon
  vs. Intel via `uname -m`), and `RunMe.sh` (Linux) each `cd` to the project folder — so the `.wav` sound
  files resolve — then run `java -cp bin Main` on the bundled runtime.
- **Per-OS release zips.** Each release ships a small, single-platform zip built with `git archive`, which
  bakes the tracked Unix executable bits into the archive so macOS/Linux players never need `chmod`.

See **[BUILD.md](BUILD.md)** for the full, reproducible build recipe (compiling, `jlink`, cross-building,
and cutting a release).

---

## ✅ Continuous integration

Because the macOS and Linux runtimes are cross-built, a
[**GitHub Actions smoke test**](.github/workflows/smoke-test.yml) verifies them on real hardware —
on every release, and on any change to the runtimes or launchers. It runs on
`windows-latest`, `ubuntu-latest` (with an Xvfb virtual display), and `macos-latest` (Apple Silicon),
and additionally exercises the Intel (`macos-x64`) runtime on the Apple Silicon runner through
**Rosetta 2**. For each runtime it:

1. checks the bundled runtime reports the right version and includes `java.desktop`;
2. **launches the actual game headlessly** and asserts it starts and stays running;
3. confirms the Unix runtime binaries keep their executable bit after a fresh checkout.

Current status: [![Smoke test bundled runtimes](https://github.com/sai3000pro/Pong/actions/workflows/smoke-test.yml/badge.svg)](https://github.com/sai3000pro/Pong/actions/workflows/smoke-test.yml)

---

## 📁 Project structure

```
Pong/
├─ src/PingPong/     Java source (Main, GameFrame, GamePanel, Paddle*, PongBall, Score*, Music, End)
├─ bin/              compiled .class files
├─ runtime/
│  ├─ windows-x64/   bundled Java runtimes, one per platform (built with jlink)
│  ├─ linux-x64/
│  ├─ macos-aarch64/
│  └─ macos-x64/
├─ *.wav             sound effects and music (Boop, Tap, Victory)
├─ RunMe.bat         Windows launcher
├─ RunMe.command     macOS launcher (auto-detects Apple Silicon / Intel)
├─ RunMe.sh          Linux launcher
├─ BUILD.md          how to rebuild the runtimes and cut a release
└─ .github/workflows/smoke-test.yml   CI that boots each runtime
```

---

## 🖼️ Screenshots

### Starting screen
![Starting screen](https://github.com/sai3000pro/Pong/assets/96095408/23d85688-aadc-44bc-8468-9525406aa044)

### The game in action
![Gameplay](https://github.com/sai3000pro/Pong/assets/96095408/727c8c23-228e-41e6-b13c-38c3c817a797)

### End screen
![End screen](https://github.com/sai3000pro/Pong/assets/96095408/df76475f-07fa-4782-9268-cd0699dd25e3)

### Gameplay video
https://github.com/sai3000pro/Pong/assets/96095408/fd4e59c8-dde5-4b8a-9fef-21dee904d09f
