package net.lax1dude.eaglercraft.demo;

import net.lax1dude.eaglercraft.EaglerRuntime;
import net.lax1dude.eaglercraft.internal.teavm.JSSurface;

import org.teavm.jso.canvas.CanvasRenderingContext2D;

/**
 * Minimal demo that proves the Java → TeaVM → JS → canvas rendering path
 * works end to end. This is a plumbing smoke test, NOT a Minecraft client.
 *
 * <p>It draws an advancing gradient checkerboard directly through the runtime
 * {@link JSSurface} so the browser pipeline (RAFlop loop + 2D context) can be
 * visually and programmatically verified. The Minecraft 26.2 client will
 * replace the callback with the real renderer; the seam is identical.</p>
 */
public final class DemoGame {

	private static int frame = 0;

	private DemoGame() {
	}

	public static void start() {
		// The surface is accessed through the runtime abstraction.
		JSSurface surface = (JSSurface) EaglerRuntime.surface();
		surface.setRenderCallback(DemoGame::render);
		surface.mount();
	}

	private static void render() {
		JSSurface surface = (JSSurface) EaglerRuntime.surface();
		CanvasRenderingContext2D c = surface.ctx2d();
		int w = surface.width();
		int h = surface.height();
		if (w <= 0 || h <= 0) {
			return;
		}

		// Simple animated checkerboard + HUD to confirm motion.
		int cell = 32;
		for (int y = 0; y < h; y += cell) {
			for (int x = 0; x < w; x += cell) {
				int shade = (((x / cell) + (y / cell) + frame / 2) & 1) * 255;
				c.setFillStyle("rgb(" + (255 - shade) + "," + (shade) + "," + (shade >> 1) + ")");
				c.fillRect(x, y, cell, cell);
			}
		}

		c.setFillStyle("#0f0");
		c.setFont("24px monospace");
		c.fillText("Eaglercraft 26.2 pipeline OK  [" + EaglerRuntime.runtimeNameSafe() + "]  frame=" + frame, 16, 32);

		frame++;
	}
}