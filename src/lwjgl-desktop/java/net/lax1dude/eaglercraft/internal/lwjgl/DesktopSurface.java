package net.lax1dude.eaglercraft.internal.lwjgl;

import net.lax1dude.eaglercraft.EaglerSurface;

/** Desktop surface backed by the AWT/Swing canvas; placeholder sizes. */
public final class DesktopSurface implements EaglerSurface {

	private int width = 1280;
	private int height = 720;

	@Override public int width() { return width; }
	@Override public int height() { return height; }

	@Override public void onFrame() {
		// Handled by the Swing EDT / LWJGL display loop in the full runtime.
	}

	@Override public void onResize(int width, int height) {
		this.width = Math.max(1, width);
		this.height = Math.max(1, height);
	}

	@Override public void setFullscreen(boolean fullscreen) {
		// no-op in the debug runtime
	}
}