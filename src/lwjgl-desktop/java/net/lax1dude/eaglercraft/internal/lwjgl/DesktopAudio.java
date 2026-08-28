package net.lax1dude.eaglercraft.internal.lwjgl;

import net.lax1dude.eaglercraft.EaglerAudio;
import net.lax1dude.eaglercraft.IAudioHandle;
import net.lax1dude.eaglercraft.IAudioResource;

/** Desktop audio plug using the bundled OpenAL/LWJGL libraries. */
public final class DesktopAudio implements EaglerAudio {

	@Override public void init() { }
	@Override public void resumeAfterUserGesture() { }
	@Override public IAudioHandle createTrack(IAudioResource resource) { return () -> false; }
	@Override public void play(IAudioHandle handle, float volume, float pitch, boolean loop) { }
	@Override public void playPositional(IAudioHandle h, float x, float y, float z, float v, float p, boolean l) { }
	@Override public void stop(IAudioHandle handle) { }
	@Override public void setMasterVolume(float volume) { }
	@Override public void pauseAll() { }
	@Override public void resumeAll() { }
	@Override public void release(IAudioHandle handle) { }
	@Override public void destroy() { }
	@Override public boolean supportsPositionalAudio() { return false; }
}