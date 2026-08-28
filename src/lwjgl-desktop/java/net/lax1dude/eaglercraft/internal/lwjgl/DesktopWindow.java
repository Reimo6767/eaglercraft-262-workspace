package net.lax1dude.eaglercraft.internal.lwjgl;

import net.lax1dude.eaglercraft.EaglerWindow;

public final class DesktopWindow implements EaglerWindow {
	@Override public boolean isFocused() { return true; }
	@Override public void requestFocus() { }
	@Override public boolean requestFullscreen(boolean fullscreen) { return false; }
	@Override public boolean isFullscreen() { return false; }
	@Override public String readClipboardText() { return null; }
	@Override public void writeClipboardText(String text) { }
	@Override public boolean isVisible() { return true; }
	@Override public boolean isTouchDevice() { return false; }
	@Override public String getWindowTitle() { return "Eaglercraft 26.2 desktop"; }
	@Override public void setWindowTitle(String title) { }
}