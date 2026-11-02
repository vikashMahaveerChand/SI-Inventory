# SI-Inventory – Project Instructions

## Overview
Java Swing desktop application supporting a pawnbroker business in India.
- Language: Java 8 (compiled with `maven.compiler.release=8`; do not use APIs or syntax newer than Java 8)
- Build: Maven
- Database: MySQL
- Base package: `com.si.inventory`; main class: `com.si.inventory.App`

## Workflow (read this at the start of every session)
1. Open `docs/STATUS.md`. It lists features by 2-digit number with a status of `TODO`, `PENDING` or `DONE`.
2. For any feature that is not `DONE`, read its spec in `docs/f-NN.md` (e.g. feature `02` -> `docs/f-02.md`).
3. Implement the feature, build it, and verify it works.
4. Set the feature to `DONE` in `docs/STATUS.md`, then commit and push to `main`.
5. If a spec is unclear or incomplete, ask the user before building.

The user adds new features by adding a row to `docs/STATUS.md` and writing `docs/f-NN.md`.

## Build and run
```
mvn package
java -jar target\si-inventory-1.0.0-SNAPSHOT.jar
```

## Conventions
- Windows environment; PowerShell is the shell.
- Keep changes focused on the requested feature; don't refactor unrelated code.
- Commit messages are short and describe the feature (e.g. "Feature 02: ...").
- Don't commit credentials; keep DB connection settings out of source control (use a local config file that is git-ignored).
