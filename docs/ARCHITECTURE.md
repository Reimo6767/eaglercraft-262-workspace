# Architecture

```
        Minecraft 26.2 (proprietary, decompiled locally)
                    │  port-src/  (never committed)
                    ▼
        Eagler platform abstraction   (src/platform-api/java)
   net.lax1dude.eaglercraft.{EaglerRuntime,EaglerPlatform,EaglerSurface,
                            EaglerGPU,EaglerInput,EaglerAudio,EaglerNetwork,
                            EaglerStorage,EaglerWindow,EaglerClock,...}
                    │
        ┌───────────┼───────────────┬─────────────────┐
        ▼           ▼               ▼                 ▼
   JS browser   WASM-GC         Desktop debug     renderer/release
   (teavm-     (wasm-gc)        (lwjgl-desktop)   (future)
    javascript)
```

## Key design decisions

### 1. Minecraft is fully separated from the browser runtime
The game code (when present) depends only on the `net.lax1dude.eaglercraft.*`
interfaces. Every browser subsystem is a per-target backend. There is exactly
one seam — `EaglerRuntime.bind(platform)` — that connects a concrete platform
to the game.

### 2. TeaVM, not a fake Java-in-JS
Java bytecode is compiled by the real forked-Eagler TeaVM toolchain
(JS `0.9.2-EAGLER`, WASM-GC `0.12.1-EAGLER-R3`) into browser output. No embedded
JVM, no hand-transpilation, no streamed desktop process.

### 3. The browser surface drives the loop
Desktop's blocking display loop is replaced by `requestAnimationFrame`
(`JSSurface`). All timers/tick-pacing go through `EaglerClock` (monotonic
`performance.now`), never blocking the main thread.

### 4. Every incompatible API is routed through a compatibility layer
Truer to the original semantics than random hacks; see `MIGRATION_MATRIX.md`.

## Subsystem map
| package / path                     | role |
|------------------------------------|------|
| `src/platform-api/java/net/lax1dude/eaglercraft/` | Eagler interfaces + runtime registry |
| `src/teavm-javascript/javascript/` | JS backends (canvas, input, audio, net, storage, clock, GPU) |
| `src/teavm-javascript/teavm-boot/` | browser entry point + demo boot |
| `src/wasm-gc-teavm/`               | WASM-GC entry (placeholder, see docs/wasm-gc.md) |
| `src/lwjgl-desktop/`               | desktop debug backends |
| `target_teavm_javascript`          | JS Gradle/TeaVM target (buildEaglerJS) |
| `target_teavm_wasm_gc`             | WASM-GC Gradle/TeaVM target (buildEaglerWASM) |
| `target_lwjgl_desktop`             | desktop debug runtime (runDesktop) |
| `reference-buildtools/`            | reused open-source build tooling (TeaVM fork classpath, ES6 shim) |
| `gradle/local-libs/`               | Eagler build-plugin jars (open source) |

## Threading note
Browsers have no userland threads that can block. The integrated server and
asset loader therefore run asynchronously (callbacks/rAF), not as blocked
`Thread`s. Blocking calls in Minecraft source are mapped to async substitutes.