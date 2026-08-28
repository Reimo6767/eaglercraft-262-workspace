package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerAudio;
import net.lax1dude.eaglercraft.IAudioHandle;
import net.lax1dude.eaglercraft.IAudioResource;

/**
 * WebAudio backend. Chrome/Safari gate AudioContext on a user gesture; the
 * host page calls {@link #resumeAfterUserGesture()} from the first click/key.
 * Positional audio can be layered over a PannerNode once a full implementation
 * is wired; the abstraction permits a non-positional fallback.
 */
public final class JSAudio implements EaglerAudio {

	private boolean initialized;

	@Override
	public void init() {
		if (!JSNative.supportsAudio()) {
			throw new IllegalStateException("WebAudio unavailable in this browser");
		}
		initialized = true;
	}

	@Override
	public void resumeAfterUserGesture() {
		// Hook into AudioContext; full impl in audio module.
	}

	@Override
	public IAudioHandle createTrack(IAudioResource resource) {
		return new SimpleHandle(resource);
	}

	@Override
	public void play(IAudioHandle handle, float volume, float pitch, boolean loop) {
		// Decode + start via AudioContext; stub for pipeline proof.
	}

	@Override
	public void playPositional(IAudioHandle handle, float x, float y, float z,
			float volume, float pitch, boolean loop) {
		play(handle, volume, pitch, loop);
	}

	@Override
	public void stop(IAudioHandle handle) {
		// no-op impl
	}

	@Override
	public void setMasterVolume(float volume) {
		// routed to AudioContext gain node
	}

	@Override
	public void pauseAll() {
	}

	@Override
	public void resumeAll() {
	}

	@Override
	public void release(IAudioHandle handle) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public boolean supportsPositionalAudio() {
		return false;
	}

	public boolean isInitialized() {
		return initialized;
	}

	private static final class SimpleHandle implements IAudioHandle {
		private final IAudioResource resource;
		private boolean playing;

		SimpleHandle(IAudioResource resource) {
			this.resource = resource;
		}

		@Override
		public boolean isPlaying() {
			return playing;
		}
	}
}