package net.lax1dude.eaglercraft.internal.teavm;

import org.teavm.jso.JSBody;
import org.teavm.jso.browser.Window;

/**
 * Browser imperative actions that have no natural declarative JSO binding in
 * TeaVM 0.9.x (pointer lock, fullscreen, clipboard). Kept minimal and central.
 */
public final class BrowserHooks {

	private BrowserHooks() {
	}

	@JSBody(params = {}, script = "var el = document.getElementById('game-canvas'); if(el && (el.requestPointerLock||el.webkitRequestPointerLock)) { var f = el.requestPointerLock||el.webkitRequestPointerLock; f.call(el); }")
	public static native void requestPointerLock();

	@JSBody(params = {}, script = "var el = document.getElementById('game-canvas'); if(el && (el.requestFullscreen||el.webkitRequestFullscreen)) { var f = el.requestFullscreen||el.webkitRequestFullscreen; f.call(el); }")
	public static native void requestFullscreen();

	@JSBody(params = {}, script = "var el = document.getElementById('game-canvas'); if(el && (el.webkitRequestFullscreen)) { el.webkitRequestFullscreen(); }")
	public static native void requestFullscreenWebkit();

	public static void fullscreen(boolean fs) {
		if (fs) {
			requestFullscreen();
		}
	}

	@JSBody(params = { "cb" }, script = "requestAnimationFrame(cb);")
	public static native void requestAnimationFrame(org.teavm.jso.JSObject cb);

	@JSBody(params = {}, script = "return window.localStorage;")
	public static native org.teavm.jso.JSObject localStorage();

	@JSBody(params = {}, script = "return document.createElement('audio');")
	public static native org.teavm.jso.JSObject newAudioElement();
}