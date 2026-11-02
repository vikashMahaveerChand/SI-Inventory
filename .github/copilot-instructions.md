# SI-Inventory – Project Instructions

## Overview
Java Swing desktop application supporting a pawnbroker business in India.
- Language: Java 8 (compiled with `options.release = 8` in Gradle; do not use APIs or syntax newer than Java 8)
- Build: Gradle
- Database: MySQL
- Base package: `com.si.inventory`; main class: `com.si.inventory.App`

## Workflow (read this at the start of every session)
1. Open `docs/STATUS.md`. It lists features by 2-digit number with a status of `TODO`, `PENDING` or `DONE`.
2. For any feature that is not `DONE`, read its spec in `docs/f-NN.md` (e.g. feature `02` -> `docs/f-02.md`).
3. Implement the feature, build it, and verify it works.
4. Set the feature to `DONE` in `docs/STATUS.md`, then report the changes and verification results to the user.
5. Do not commit or push changes unless the user explicitly confirms.
6. If a spec is unclear or incomplete, ask the user before building.

The user adds new features by adding a row to `docs/STATUS.md` and writing `docs/f-NN.md`.

## Build and run
```
.\gradlew build
java -jar build\libs\si-inventory-1.0.0-SNAPSHOT.jar
```

## Conventions
- Windows environment; PowerShell is the shell.
- Keep changes focused on the requested feature; don't refactor unrelated code.
- Commit messages are short and describe the feature (e.g. "Feature 02: ...").
- Don't commit credentials; keep DB connection settings out of source control (use a local config file that is git-ignored).

## Keyboard-first user experience
- Design the application so common workflows can be completed with the keyboard; mouse interaction should be secondary.
- Pressing Enter in a text or password field must move focus to the next control, like Tab, and must not submit or close a dialog.
- For a drop-down control, the first Enter opens the drop-down; the next Enter commits the highlighted selection.
- Enter on a focused button must activate that button's own action. Do not let Enter in another control trigger a dialog's default button.
