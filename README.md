# Eaglercraft 26.2 Workspace

Minecraft **Java Edition 26.2** → Eagler runtime → **TeaVM** → browser.

A serious source-porting workspace that consumes a legitimate local decompile of
Minecraft Java Edition 26.2, patches out desktop/LWJGL-only APIs, and compiles
the result through the real Eagler-fork TeaVM toolchain into a browser client
(JavaScript today; WASM-GC documented as experimental).

> **Version integrity.** This workspace is built around 26.2. It does not rename
> a 1.8/1.14/26.1 client and label it 26.2. Until a legitimate 26.2 decompiled
> source is provided, the browser targets compile the **Eagler runtime + a
> labelled pipeline demo** (not a fake full client). Every claimed feature is
> gated on a passing real-browser test — see `docs/FEATURE_PARITY.md`.

## Links
- **Repository:** https://github.com/NT9712/eaglercraft-262-workspace
- **Docs & live client:** https://deploy-docs-p3pl65k7r-nixeltide-8187s-projects.vercel.app
  (open in a browser; the live client demo is under `/client/` — Note: Vercel
   free-tier Deployment Protection may prompt for login on first visit.)

## Status (verified in real headless Chromium)
- ✅ TeaVM JS client **boots** — zero console errors, canvas renders.
- ✅ Migration matrix + TeaVM pipeline + Eagler runtime seam.
- 🟡 WASM-GC / desktop debug scaffolded (see docs).
- ➖ Full 26.2 gameplay requires the legitimate decompiled source (see
  `docs/source-input.md`).

## Requirements
- Java 17 (toolchain enforced by Gradle)
- Gradle 8.14 (wrapper included, auto-downloads)
- Node 20+ (only for the optional browser smoke test)
- A legitimate local copy of the Minecraft 26.2 client JAR (to port the real game)

## Build the browser (JavaScript) client
```bash
./gradlew :target_teavm_javascript:buildEaglerJS
./gradlew :target_teavm_javascript:stageClient
python3 -m http.server 8080 --directory dist/client
# open http://localhost:8080
```

## Browser smoke test
```bash
node tests/browser_smoke.js /path/to/chrome
```

## Full documented commands
See `docs/`:
- `SOURCE.md`-style input setup → `docs/source-input.md`
- migration matrix → `docs/MIGRATION_MATRIX.md`
- feature parity → `docs/FEATURE_PARITY.md`
- architecture → `docs/ARCHITECTURE.md`
- WASM-GC status → `docs/wasm-gc.md`

## License / redistribution
This repository does **not** include decompiled Minecraft source or assets. All
bundled tooling (Gradle wrapper, TeaVM, Eagler fork build plugin, desktop runtime
native libs) is third-party open source and retains its own licenses under
`desktopRuntime/*.txt` and Gradle metadata. Any `desktopRuntime` runtime-state
files (storage dumps, download caches) are gitignored.