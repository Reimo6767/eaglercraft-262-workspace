package net.lax1dude.eaglercraft.internal.wasm_gc_teavm;

import net.lax1dude.eaglercraft.EaglerRuntime;

/**
 * Placeholder entry for the WASM-GC target.
 *
 * <p>This class exists so the wasm-gc source set is valid and can be compiled.
 * It intentionally does NOT boot a fake client: the full WASM-GC browser
 * harness (JSPI runtime, WebGL/WebSocket JSO bridges, Closure bundle step) is
 * not installed in this bootstrap workspace, so booting here without the real
 * harness would only produce a non-functional module. See docs/wasm-gc.md.</p>
 */
public final class MainClass {

	private MainClass() {
	}

	public static void main(String[] args) {
		EaglerRuntime.runtimeNameSafe();
	}
}