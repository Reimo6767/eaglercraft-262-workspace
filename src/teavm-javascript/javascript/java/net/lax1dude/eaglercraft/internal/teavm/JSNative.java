package net.lax1dude.eaglercraft.internal.teavm;

import org.teavm.jso.JSBody;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.browser.Window;

/**
 * Single seam between TeaVM Java and the raw browser DOM/JS world. Keeping
 * every direct browser interaction here lets the rest of the runtime remain
 * pure portable Java.
 */
public final class JSNative {

	private static HTMLDocument cachedDoc = null;
	private static Window cachedWindow = null;

	private JSNative() {
	}

	public static Window window() {
		if (cachedWindow == null) {
			cachedWindow = Window.current();
		}
		return cachedWindow;
	}

	public static HTMLDocument doc() {
		if (cachedDoc == null) {
			cachedDoc = window().getDocument();
		}
		return cachedDoc;
	}

	@JSBody(params = {}, script = "return performance.now();")
	public static native double performanceNow();

	@JSBody(params = {}, script = "return typeof WebGL2RenderingContext !== 'undefined';")
	public static native boolean supportsWebGL2();

	@JSBody(params = {}, script = "return typeof AudioContext !== 'undefined' || typeof webkitAudioContext !== 'undefined';")
	public static native boolean supportsAudio();

	@JSBody(params = {}, script = "return 'indexedDB' in window;")
	public static native boolean supportsIndexedDB();

	@JSBody(params = {}, script = "return 'WebSocket' in window;")
	public static native boolean supportsWebSocket();

	@JSBody(params = {}, script = "return 'requestPointerLock' in document.body || 'webkitRequestPointerLock' in document.body;")
	public static native boolean supportsPointerLock();

	@JSBody(params = {}, script = "return 'requestFullscreen' in document.body || 'webkitRequestFullscreen' in document.body;")
	public static native boolean supportsFullscreen();

	@JSBody(params = {}, script = "return ('ontouchstart' in window) || (navigator.maxTouchPoints !== undefined && navigator.maxTouchPoints > 0);")
	public static native boolean isTouchDevice();

	@JSBody(params = {}, script = "return document.visibilityState !== 'hidden';")
	public static native boolean pageVisible();

	@JSBody(params = {}, script = "return navigator.platform || '';")
	public static native String platformString();

	@JSBody(params = {}, script = "return navigator.hardwareConcurrency || 1;")
	public static native int hardwareConcurrency();

	@JSBody(params = {}, script = "return navigator.language || 'en-US';")
	public static native String language();

	@JSBody(params = {}, script = "return document.title;")
	public static native String getTitle();

	@JSBody(params = { "t" }, script = "document.title = t;")
	public static native void setTitle(String t);

	@JSBody(params = { "text" }, script = "navigator.clipboard.writeText(text);")
	public static native void writeClipboard(String text);

	@JSBody(params = {}, script = "return navigator.userAgent;")
	public static native String userAgent();
}