package net.lax1dude.eaglercraft;

/**
 * Window / document lifecycle abstraction.
 *
 * <p>Handles focus (needed to clear held keys on blur), fullscreen requests,
 * clipboard access, and visibility state. Browser methods are non-blocking
 * asynchronous calls into DOM APIs.</p>
 */
public interface EaglerWindow {

	boolean isFocused();

	void requestFocus();

	/** Request fullscreen, returning true if the request was initiated. */
	boolean requestFullscreen(boolean fullscreen);

	boolean isFullscreen();

	String readClipboardText();

	void writeClipboardText(String text);

	/** Page visibility (tab hidden => pause audio / network flush). */
	boolean isVisible();

	/** True when touch input is available (mobile). */
	boolean isTouchDevice();

	String getWindowTitle();

	void setWindowTitle(String title);
}