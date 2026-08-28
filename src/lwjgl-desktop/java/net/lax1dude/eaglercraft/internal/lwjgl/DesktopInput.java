package net.lax1dude.eaglercraft.internal.lwjgl;

import net.lax1dude.eaglercraft.EaglerInput;

/** Desktop input via java.awt; debug quality only. */
public final class DesktopInput implements EaglerInput {

	@Override public void poll() { }
	@Override public boolean isKeyDown(int keyCode) { return false; }
	@Override public boolean isKeyPressed(int keyCode) { return false; }
	@Override public boolean isMouseButtonDown(int button) { return false; }
	@Override public float mouseDX() { return 0f; }
	@Override public float mouseDY() { return 0f; }
	@Override public float scrollDelta() { return 0f; }
	@Override public void attemptPointerLock() { }
	@Override public boolean isPointerLockActive() { return false; }
	@Override public int keyCodeFromString(String javaKey) { return javaKey == null ? -1 : (javaKey.length() == 1 ? javaKey.charAt(0) : -1); }
	@Override public void consumeMotion() { }
	@Override public void clearFrameState() { }
}