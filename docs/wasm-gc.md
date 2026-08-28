# WebAssembly GC (WASM-GC) target — status

## Summary
TeaVM's WASM-GC backend can compile the pure-Java core to `classes.wasm`.
However, a **runnable browser client** requires more than the Java module, and
those pieces are not shipped in this bootstrap workspace. We therefore do NOT
produce a fake `.wasm` that does not actually drive the client. The JavaScript
target is the supported path today.

## Why WASM-GC is not yet shippable here

1. **Runtime harness.** WASM-GC needs a large JS host harness: JSO bridges for
   WebGL / WebSocket / IndexedDB, `platformRuntime.js`, `platformApplication.js`,
   and friends, plus a `teavm_runtime.js` loader that performs the
   `WebAssembly.instantiate` dance and drives JSPI.
2. **Closure-Compiler bundling.** The Eagler fork bundles the harness JS with
   the Closure Compiler into `eagruntime.js`, and packages an EPK asset bundle
   with `MakeWasmClientBundle`. Both require installed `buildtools` (a Closure
   compiler jar) not present here.
3. **Browser support.** Even when built, WASM-GC needs WebAssembly GC (**wasm-gc**)
   plus JSPI (**WebAssembly JavaScript Promise Integration**), which are still
   experimental and not enabled by default in all browsers (notably Safari).

## What IS wired
- `target_teavm_wasm_gc/build.gradle.kts` configures the TeaVM WASM-GC compile
  (`teavm.wasmGC { mainClass=... wasm_gc_teavm.MainClass }`).
- The wasm-gc source set is valid and compilable; its `MainClass` is an honest
  placeholder that does not fake a boot.

## Revisit checklist (future)
- [ ] Install `buildtools/closure-compiler.jar`
- [ ] Add the Eagler WASM host-harness JS files under `src/teavm-wasm-gc/`
- [ ] Add the JSPI bootstrap (`compileWasmBootstrap`) + EPK packaging (`makeMainWasmClientBundle`)
- [ ] Enable the wasm build task end-to-end
- [ ] Verify in a wasm-gc-capable browser, then mark parity

Until then the JavaScript target is the working, tested client.