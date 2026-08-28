package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerWindow;

/**
 * Window/document/visibility abstraction for the browser.
 */
public final class JSWindow implements EaglerWindow {

	@Override
	public boolean isFocused() {
		return JSNative.pageVisible();
	}

	@Override
	public void requestFocus() {
	}

	@Override
	public boolean requestFullscreen(boolean fullscreen) {
		if (fullscreen) {
			BrowserHooks.requestFullscreen();
		}
		return fullscreen;
	}

	@Override
	public boolean isFullscreen() {
		return false;
	}

	@Override
	public String readClipboardText() {
		return null;
	}

	@Override
	public void writeClipboardText(String text) {
		JSNative.writeClipboard(text);
	}

	@Override
	public boolean isVisible() {
		return JSNative.pageVisible();
	}

	@Override
	public boolean isTouchDevice() {
		return JSNative.isTouchDevice();
	}

	@Override
	public String getWindowTitle() {
		return JSNative.getTitle();
	}

	@Override
	public void setWindowTitle(String title) {
		JSNative.setTitle(title);
	}
}