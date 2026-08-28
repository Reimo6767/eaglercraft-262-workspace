package net.lax1dude.eaglercraft;

/**
 * Input abstraction. Browser backend transforms DOM/W3C events (keyboard,
 * mouse, pointer lock, touch, wheel, fullscreen, focus) into this neutral
 * stream. Desktop backend forwards LWJGL callbacks into the same stream.
 *
 * <p>The backend is responsible for browser edge cases: suppressing page
 * scroll on wheel, releasing capture on pointer-lock loss, and never leaving
 * the client stuck with a held key after losing focus (blur clears the key
 * state).</p>
 */
public interface EaglerInput {

	void poll();

	boolean isKeyDown(int keyCode);

	/** @return true when the key was pressed since the last poll (edge-triggered). */
	boolean isKeyPressed(int keyCode);

	boolean isMouseButtonDown(int button);

	/** Delta movement in pixels since last poll. */
	float mouseDX();

	float mouseDY();

	/** Scroll delta in "notches" since last poll. */
	float scrollDelta();

	/** Request pointer lock if supported; callbacks delivered via onPointerLockState. */
	void attemptPointerLock();

	boolean isPointerLockActive();

	int keyCodeFromString(String javaKey);

	void consumeMotion();

	/** Clears all transient pressed/motion state (used on blur / frame end). */
	void clearFrameState();
}