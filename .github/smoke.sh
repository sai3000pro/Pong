#!/usr/bin/env bash
# Smoke-test one bundled runtime: launch the game headlessly and confirm it
# starts and stays up. On Linux, wrap in a virtual display (Xvfb).
# Usage: smoke.sh <path-to-java> <label>
set -u
JAVA="$1"
LABEL="${2:-runtime}"

echo "== $LABEL: runtime sanity =="
"$JAVA" -version
"$JAVA" --list-modules | grep -q '^java.desktop' && echo "java.desktop module present"

echo "== $LABEL: headless launch =="
launch() {
  if [ "${RUNNER_OS:-}" = "Linux" ]; then
    xvfb-run -a "$JAVA" -cp bin Main
  else
    "$JAVA" -cp bin Main
  fi
}

launch > "game-$LABEL.log" 2>&1 &
PID=$!
ok=0
for i in $(seq 1 15); do
  if ! kill -0 "$PID" 2>/dev/null; then
    wait "$PID"; code=$?
    if [ "$code" -eq 0 ]; then
      echo "Game exited cleanly after ${i}s"; ok=1
    else
      echo "FAIL: $LABEL exited early (code $code) after ${i}s"
    fi
    break
  fi
  sleep 1
done
if kill -0 "$PID" 2>/dev/null; then
  echo "PASS: $LABEL launched and stayed up for 15s"
  ok=1
  kill "$PID" 2>/dev/null || true
fi

echo "----- game-$LABEL.log -----"
cat "game-$LABEL.log" 2>/dev/null || true
[ "$ok" -eq 1 ]
