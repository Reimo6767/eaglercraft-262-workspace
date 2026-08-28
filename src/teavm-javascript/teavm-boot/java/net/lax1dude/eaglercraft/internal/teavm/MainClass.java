package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerPlatform;
import net.lax1dude.eaglercraft.EaglerRuntime;
import net.lax1dude.eaglercraft.demo.DemoGame;

/**
 * Browser (TeaVM JS) entry point.
 *
 * <p>This class compiles through the Eagler forked TeaVM toolchain into
 * browser JavaScript. It binds the concrete browser {@link EaglerPlatform}
 * and hands control to the demo/game bootstrap. When the proprietary
 * Minecraft 26.2 client bootstrap is present in {@code src/game/java}, the
 * {@code DemoGame} is replaced by the real {@code Minecraft} launcher path;
 * the seam is identical.</p>
 */
public final class MainClass {

	private MainClass() {
	}

	/**
	 * TeaVM maps this method to {@code entryPointName "main"} in the generated
	 * script; the surrounding HTML calls {@code main()} after the script loads.
	 */
	public static void main(String[] args) {
		EaglerPlatform platform = new JSPlatform();
		EaglerRuntime.bind(platform);

		DemoGame.start();
	}
}