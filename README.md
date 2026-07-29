# Pong
The classic 1970s Pong game, remade! First to 5 points wins!
This project was created for a school ICS4U assignment.

## How to run
**No Java installation required** — a Java runtime is bundled with the project.

1. Download the project (green **Code** button → **Download ZIP**, then unzip) or clone it.
2. Launch it for your operating system:
   - **Windows:** double-click **`RunMe.bat`**
   - **macOS:** double-click **`RunMe.command`**
   - **Linux:** run **`./RunMe.sh`** (or double-click if your file manager allows)

That's it — the game window opens. No install, no internet, no setup.

> **macOS note:** the bundled runtime is unsigned, so Gatekeeper may block it on first launch (especially if you downloaded the ZIP via a browser, which "quarantines" it). The reliable fix is to open **Terminal** in the project folder and run once:
> ```sh
> xattr -dr com.apple.quarantine .
> chmod +x RunMe.command
> ```
> Then double-click `RunMe.command` (or run `./RunMe.command`). Cloning with `git` instead of downloading the ZIP usually avoids the quarantine entirely.

### Starting screen:
![image](https://github.com/sai3000pro/Pong/assets/96095408/23d85688-aadc-44bc-8468-9525406aa044)

### The game in action:
 ![image](https://github.com/sai3000pro/Pong/assets/96095408/727c8c23-228e-41e6-b13c-38c3c817a797)

### End screen:
![image](https://github.com/sai3000pro/Pong/assets/96095408/df76475f-07fa-4782-9268-cd0699dd25e3)

### Gameplay:
https://github.com/sai3000pro/Pong/assets/96095408/fd4e59c8-dde5-4b8a-9fef-21dee904d09f

## Controls

Press ```space``` to start the game.

Player 1: ```w``` to move paddle up, ```s``` to move paddle down.

Player 2: ```↑``` to move paddle up, ```↓``` to move paddle down.
