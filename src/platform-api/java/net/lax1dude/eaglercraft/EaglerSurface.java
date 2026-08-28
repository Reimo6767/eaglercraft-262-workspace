package net.lax1dude.eaglercraft;

/**
 * Rasterization surface the game renders onto (browser &lt;canvas&gt; or an
 * LWJGL framebuffer). Independently transports requestAnimationFrame so the
 * game loop is driven by the browser compositor rather than blocking threads.
 */
public interface EaglerSurface {

	/** Width in device pixels. */
	int width();

	/** Height in device pixels. */
	int height();

	/** Request a single animation tick; invoked on the rendering thread. */
	void onFrame();

	/** Invoked when the backing surface is resized. */
	void onResize(int width, int height);

	/** Request the surface to be made fullscreen where the platform supports it. */
	void setFullscreen(boolean fullscreen);
}