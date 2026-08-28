# Eaglercraft 26.2 — Documentation

Porting Minecraft Java Edition **26.2** to the browser via the Eagler runtime and
**TeaVM** (JavaScript target; WASM-GC experimental).

## Contents
- [README](../README.md) — status, requirements, quick build
- [Architecture](ARCHITECTURE.md) — layered design and subsystem map
- [Migration matrix](MIGRATION_MATRIX.md) — 26.2 API → Eagler browser path
- [Feature parity gauntlet](FEATURE_PARITY.md) — tested ✓/✗ checklist
- [Source input setup](source-input.md) — legitimate local 26.2 input
- [WASM-GC status](wasm-gc.md) — why it is experimental and what is wired

## Verified milestones (real headless Chromium)
1. ✅ **Boot**: TeaVM JS client executes, zero console errors.
2. ✅ **Rendering path**: rAF loop draws to canvas (non-blank frames).
3. ✅ **TeaVM pipeline**: Java bytecode → browser JS actually runs.
4. ✅ **Eagler runtime seam**: `EaglerRuntime.bind()` walls game from browser.

## The build pipeline
```
Local Minecraft 26.2 JAR (legitimate input)
        → decompile → port-src/minecraft-26.2/
        → Eagler patches → Java 17 sources
        → TeaVM compile → dist/client/classes.js
        → Browser runtime + asset packaging → final client
```