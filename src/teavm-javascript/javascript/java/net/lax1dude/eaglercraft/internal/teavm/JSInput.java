package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerInput;

/**
 * Browser keyboard/mouse/pointer-lock input backend. Transforms W3C DOM events
 * into the neutral {@link EaglerInput} stream, and defensively handles the
 * browser's focus/pointer-lock edge cases (clearing held keys on blur).
 */
public final class JSInput implements EaglerInput {

	private boolean[] keysDown = new boolean[512];
	private int[] pressedTimer = new int[512];
	private boolean[] mouseButtons = new boolean[8];
	private float dx;
	private float dy;
	private float scroll;
	private boolean pointerLocked;

	@Override
	public void poll() {
		// Per-frame state transitions happen here / or driven by event callbacks
		// which are registered by the host page (see browser hooks module).
	}

	public void keyDown(int code) {
		if (code >= 0 && code < keysDown.length && !keysDown[code]) {
			keysDown[code] = true;
			pressedTimer[code] = 1;
		}
	}

	public void keyUp(int code) {
		if (code >= 0 && code < keysDown.length) {
			keysDown[code] = false;
		}
	}

	public void mouseMove(float ddx, float ddy) {
		dx += ddx;
		dy += ddy;
	}

	public void mouseButton(int button, boolean down) {
		if (button >= 0 && button < mouseButtons.length) {
			mouseButtons[button] = down;
		}
	}

	public void setScroll(float delta) {
		scroll += delta;
	}

	public void setPointerLock(boolean locked) {
		this.pointerLocked = locked;
	}

	@Override
	public boolean isKeyDown(int keyCode) {
		return keyCode >= 0 && keyCode < keysDown.length && keysDown[keyCode];
	}

	@Override
	public boolean isKeyPressed(int keyCode) {
		if (keyCode >= 0 && keyCode < keysDown.length && pressedTimer[keyCode] > 0) {
			pressedTimer[keyCode]--;
			return pressedTimer[keyCode] == 0 && keysDown[keyCode];
		}
		return false;
	}

	@Override
	public boolean isMouseButtonDown(int button) {
		return button >= 0 && button < mouseButtons.length && mouseButtons[button];
	}

	@Override
	public float mouseDX() {
		return dx;
	}

	@Override
	public float mouseDY() {
		return dy;
	}

	@Override
	public float scrollDelta() {
		return scroll;
	}

	@Override
	public void attemptPointerLock() {
		BrowserHooks.requestPointerLock();
	}

	@Override
	public boolean isPointerLockActive() {
		return pointerLocked;
	}

	@Override
	public int keyCodeFromString(String javaKey) {
		if (javaKey == null) {
			return -1;
		}
		// PC keyboard codes: 32=space, keys letters map to their ascii
		switch (javaKey) {
		case "Space": return 32;
		case "Enter": return 13;
		case "W": return 87;
		case "A": return 65;
		case "S": return 83;
		case "D": return 68;
		case "E": return 69;
		case "Q": return 81;
		case "Left Shift": return 16;
		case "Left Control": return 17;
		case "Left Alt": return 18;
		default: return javaKey.length() == 1 ? javaKey.charAt(0) : -1;
		}
	}

	@Override
	public void consumeMotion() {
		dx = 0;
		dy = 0;
		scroll = 0;
	}

	@Override
	public void clearFrameState() {
		consumeMotion();
		// Held keys are not cleared per-frame (they represent continuous state);
		// the blur handler clears them to avoid "stuck" keys.
	}
}