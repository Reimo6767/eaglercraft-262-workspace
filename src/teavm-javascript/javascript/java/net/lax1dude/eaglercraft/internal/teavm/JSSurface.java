package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerSurface;

import org.teavm.jso.browser.AnimationFrameCallback;
import org.teavm.jso.browser.Window;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;

/**
 * Canvas surface driven by requestAnimationFrame. This replaces the blocking
 * display-update loop of desktop Minecraft with the browser compositor. The
 * owning game registers a render callback ({@link #setRenderCallback}) and is
 * then driven each compositor tick; the EaglerSurface contract is honoured by
 * forwarding {@link #onFrame()} onto that callback.
 */
public final class JSSurface implements EaglerSurface, AnimationFrameCallback {

	private final HTMLCanvasElement canvas;
	private final CanvasRenderingContext2D ctx2d;

	private Runnable renderCallback = () -> {
	};
	private int width;
	private int height;

	public JSSurface() {
		HTMLDocument doc = JSNative.doc();
		this.canvas = (HTMLCanvasElement) doc.getElementById("game-canvas");
		resize();
		this.ctx2d = (CanvasRenderingContext2D) canvas.getContext("2d");
	}

	public void mount() {
		Window.requestAnimationFrame(this);
	}

	public void setRenderCallback(Runnable cb) {
		this.renderCallback = cb;
	}

	public CanvasRenderingContext2D ctx2d() {
		return ctx2d;
	}

	private void resize() {
		Window w = JSNative.window();
		this.width = w.getInnerWidth();
		this.height = w.getInnerHeight();
		canvas.setWidth(Math.max(1, width));
		canvas.setHeight(Math.max(1, height));
		canvas.getStyle().setProperty("width", width + "px");
		canvas.getStyle().setProperty("height", height + "px");
	}

	@Override
	public int width() {
		return width;
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public void onFrame() {
		renderCallback.run();
	}

	@Override
	public void onResize(int width, int height) {
		resize();
	}

	@Override
	public void setFullscreen(boolean fullscreen) {
		// Fullscreen request handled in JSWindow (document-level),
		// this is the surface owner of the canvas.
	}

	@Override
	public void onAnimationFrame(double timestamp) {
		renderCallback.run();
		Window.requestAnimationFrame(this);
	}
}