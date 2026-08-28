package net.lax1dude.eaglercraft;

/**
 * The Eagler platform contract.
 *
 * <p>This is the single seam between Minecraft (proprietary decompiled
 * source, injected at build time into {@code src/game/java}) and the
 * browser/WASM runtime. Every subsystem is exposed through an interface
 * so that the game code depends only on stable abstractions and never
 * on browser internals.</p>
 *
 * <p>Layering:
 * <pre>
 *   Minecraft 26.2 (src/game/java)
 *        |
 *        v
 *   Eagler platform abstraction (this package)
 *        |
 *        v
 *   Browser implementation (per-target, e.g. teavm-javascript)
 * </pre>
 * </p>
 */
public final class EaglerRuntime {

	private static volatile EaglerPlatform platform = null;
	private static volatile EaglerSurface surface = null;
	private static volatile EaglerInput input = null;
	private static volatile EaglerAudio audio = null;
	private static volatile EaglerNetwork network = null;
	private static volatile EaglerStorage storage = null;
	private static volatile EaglerWindow window = null;
	private static volatile EaglerGPU gpu = null;
	private static volatile EaglerClock clock = null;

	private EaglerRuntime() {
	}

	public static void bind(EaglerPlatform platform) {
		EaglerRuntime.platform = platform;
		surface = platform.surface();
		input = platform.input();
		audio = platform.audio();
		network = platform.network();
		storage = platform.storage();
		window = platform.window();
		gpu = platform.gpu();
		clock = platform.clock();
	}

	public static EaglerSurface surface() {
		return require(surface, "surface");
	}

	public static EaglerInput input() {
		return require(input, "input");
	}

	public static EaglerAudio audio() {
		return require(audio, "audio");
	}

	public static EaglerNetwork network() {
		return require(network, "network");
	}

	public static EaglerStorage storage() {
		return require(storage, "storage");
	}

	public static EaglerWindow window() {
		return require(window, "window");
	}

	public static EaglerGPU gpu() {
		return require(gpu, "gpu");
	}

	public static EaglerClock clock() {
		return require(clock, "clock");
	}

	/** Returns the bound platform's runtime name, or "unbound" if not yet wired. */
	public static String runtimeNameSafe() {
		EaglerPlatform p = platform;
		return p != null ? p.runtimeName() : "unbound";
	}

	private static <T> T require(T value, String name) {
		if (value == null) {
			throw new IllegalStateException(
				"EaglerRuntime subsystem '" + name + "' has not been bound. "
				+ "Call EaglerRuntime.bind() with a concrete platform before use.");
		}
		return value;
	}
}