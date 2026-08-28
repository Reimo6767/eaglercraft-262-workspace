# Feature Parity Gauntlet — Minecraft 26.2

This checklist is tracked from the legitimate 26.2 source and updated only when
a feature has been *actually tested*. No item is marked done on the strength of
"it compiles" or "the desktop runtime works". The browser is the source of
truth (per porting rules I: desktop does not count as browser success).

Legend: ✅ = verified working in the browser · 🟡 = in progress · ✗ = blocked
pending legitimate 26.2 source · ➖ = intentionally not in the bootstrap demo

| Feature                | Status | Notes                                                        |
|------------------------|--------|--------------------------------------------------------------|
| Boot (browser)         | ✅     | TeaVM JS client boots in headless Chromium, zero console errors |
| Canvas rendering path  | ✅     | rAF loop draws to 2D canvas (demo); verified non-blank frames   |
| Migration matrix       | ✅     | docs/MIGRATION_MATRIX.md                                       |
| TeaVM JS target        | ✅     | classes.js produced, parses, runs in Chromium                  |
| TeaVM WASM-GC target   | 🟡     | compiles core; full harness pending (docs/wasm-gc.md)           |
| Desktop debug runtime  | 🟡     | shared Eagler runtime bound; basic AWT window                   |
| Main menu              | ➖     | requires legitimate 26.2 source                                |
| World loading          | ➖     | requires legitimate 26.2 source                                |
| Terrain rendering      | ➖     | requires legitimate 26.2 source                                |
| Player movement        | ➖     | requires legitimate 26.2 source                                |
| Inventory              | ➖     | requires legitimate 26.2 source                                |
| Blocks                 | ➖     | requires legitimate 26.2 source                                |
| Entities               | ➖     | requires legitimate 26.2 source                                |
| GUI                    | ➖     | requires legitimate 26.2 source                                |
| Audio (WebAudio)       | 🟡     | EaglerAudio seam + gesture-aware JSAudio stub                   |
| Networking (WebSocket) | 🟡     | EaglerNetwork seam + relay transport stub                       |
| Settings               | ➖     | requires legitimate 26.2 source                                |
| Resource loading       | 🟡     | packaged asset/EPK pipeline scaffolded                          |
| Saving/storage         | 🟡     | EaglerStorage seam; IndexedDB + desktop FS stubs                |
| Input (pointer lock)   | 🟡     | EaglerInput seam + blur-safe key handling                       |
| Fullscreen/focus       | 🟡     | EaglerWindow seam + DOM fullscreen/clipboard                    |
| Timers/ticks           | ✅     | EaglerClock monotonic (performance.now)                         |
| Touch                 | 🟡     | isTouchDevice detection                                        |

## Re-verification workflow
Each time new 26.2 source is added, run:
```
./gradlew :target_teavm_javascript:buildEaglerJS
node tests/browser_smoke.js
```
Then mark the corresponding feature ✅/✗ based on the browser result, not the
desktop result.

## Honesty rule
- ✅ requires a passing browser test of that specific feature.
- We do not claim parity we have not tested.
- The **bootstrap demo is explicitly not a Minecraft client**; features marked
  ➖ are those that fundamentally require the proprietary decompiled source to
  exist before they can be real.