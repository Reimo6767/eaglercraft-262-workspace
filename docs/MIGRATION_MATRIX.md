# Minecraft 26.2 → Eagler Migration Matrix

This matrix is the living result of the API audit. For every Minecraft 26.2
subsystem we record: **can it run as-is?** and **what Eagler implementation
replaces it**. This is seeded from the known behavior of the Minecraft 26.2
client architecture and the Eaglercraft runtime surface; it must be re-verified
against the actual decompiled 26.2 source once it is provided.

Legend: ✅ retain · 🟡 adapt · 🚫 replace

| Minecraft 26.2 subsystem          | Native impl           | Run as-is in browser? | Eagler path                                                        | Status |
|-----------------------------------|-----------------------|-----------------------|--------------------------------------------------------------------|--------|
| Java runtime model                | JVM                   | 🚫                    | TeaVM (JS/WASM-GC). No embedded JVM, no transpile-by-hand           | ✅ wired |
| Main thread / update loop         | Blocking loop         | 🚫                    | `EaglerSurface` + rAF driver (`JSSurface`)                          | ✅ wired |
| Native threads/executors          | java.lang.Thread      | 🚫                    | Restricted; rAF/timer driven. `EaglerClock` pacing                  | 🟡 stubbed |
| LWJGL windowing                   | GLFW/LWJGL            | 🚫                    | `EaglerWindow` → DOM/fullscreen/pointer lock                        | ✅ wired |
| OpenGL rendering                  | LWJGL GL               | 🚫                    | `EaglerGPU` → WebGL2 backend (JSWebGL2GPU)                          | 🟡 concrete impl pending |
| Framebuffer / textures / shaders  | GL objects            | 🚫                    | Mapped onto WebGL texture/buffer/program handles                    | 🟡 stub |
| Keyboard / mouse input            | GLFW callbacks        | 🚫                    | `EaglerInput` → DOM events + pointer lock                           | ✅ wired |
| Audio (OpenAL)                    | OpenAL                | 🚫                    | `EaglerAudio` → WebAudio (gesture-aware)                            | 🟡 stub |
| Networking (TCP)                  | raw sockets           | 🚫                    | `EaglerNetwork` → WebSocket relay (wss://) / integrated loopback    | 🟡 stub |
| Filesystem                        | java.io.File          | 🚫                    | `EaglerStorage` → IndexedDB virtual FS                              | 🟡 stub |
| Clipboard                         | AWT/GLFW              | 🚫                    | `EaglerWindow.writeClipboardText` → navigator.clipboard             | ✅ wired |
| Fullscreen / focus                | GLFW                 | 🚫                    | `EaglerWindow.requestFullscreen` + visibility change               | ✅ wired |
| Timers / ticks                    | System.nanoTime / sleep| 🚫 (blocking sleep)  | `EaglerClock` monotonic (performance.now)                           | ✅ wired |
| Touch input                       | n/a                   | 🟡                    | detected via `EaglerWindow.isTouchDevice`                           | 🟡 stub |
| Shaders/GLSL                      | desktop GLSL          | 🟡                    | prefixed for WebGL2 (`defaultShaderPrefix`)                         | 🟡 stub |
| Loadable resources                | jar/classpath FS      | 🟡                    | packaged asset bundle pushed through `EaglerStorage`/EPK pipeline   | 🟡 stub |
| World save format                 | native directory      | 🚫                    | browser VFS storing region/anvil data                               | 🟡 stub |
| Multiplayer protocol              | TCP                        | 🚫                    | Eagler relay sub-protocol over WebSocket                            | 🟡 stub |
| Reflection / Unsafe               | JVM internals         | 🚫                    | TeaVM unsupported → replaced with direct calls/TEA VM-compatible    | 🔍 audit |
| java.util.concurrent blocking     | native threads        | 🚫                    | async substitutes (listeners/callbacks)                            | 🔍 audit |
| JNI / native libs (LWJGL .so)     | native                | 🚫                    | none — browser has no native code path                              | ✅ none |

## Incompatibility register

Continuously updated list of APIs unresolved by the browser/TeaVM model.

| API / class                       | Problem                                    | Mitigation                                              | Open |
|-----------------------------------|--------------------------------------------|---------------------------------------------------------|------|
| `java.lang.Thread`                | no true threads in main-thread JSO model   | rAF + cooperated scheduling; integrated server runs async | yes |
| `java.net.Socket`                 | no TCP in browser                          | Eagler WebSocket relay                                    | yes |
| `java.io.File`                    | no POSIX FS in browser                     | `EaglerStorage` virtual FS                                | yes |
| `sun.misc.Unsafe` / reflection    | TeaVM restrictions                         | rewrite to TeaVM-safe allocations                        | audit |
| LWJGL `*GL` static surfaces       | JNI/native GL                             | `EaglerGPU` interface                                    | yes |
| OpenAL `alSource*`                | native audio                              | WebAudio `EaglerAudio`                                    | yes |
| AWT/GLFW window class             | desktop windowing                         | `EaglerWindow` + DOM canvas                              | yes |

## Workflow rule

Every incompatible subsystem flows through the compatibility layer — never
by scattering browser hacks. Region of ownership:

```
   Original Minecraft implementation   (port-src/)
        ↓ patch
   Compatibility layer                 (Eagler platform-api)
        ↓
   Eagler implementation               (per-target backends)
```