package net.lax1dude.eaglercraft;

/**
 * Aggregates all subsystem factories that a concrete runtime (browser JS,
 * browser WASM-GC, or desktop LWJGL debug) must provide.
 */
public interface EaglerPlatform {

	EaglerSurface surface();

	EaglerInput input();

	EaglerAudio audio();

	EaglerNetwork network();

	EaglerStorage storage();

	EaglerWindow window();

	EaglerGPU gpu();

	EaglerClock clock();

	/** Human-readable runtime name, e.g. "teavm-javascript". */
	String runtimeName();

	/** True when this runtime is actually executing on a browser main thread. */
	boolean isBrowserRuntime();
}