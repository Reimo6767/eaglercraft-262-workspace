package net.lax1dude.eaglercraft;

/**
 * Audio abstraction, backed by WebAudio on browsers and OpenAL on desktop.
 *
 * <p>The browser backend must honour the user-gesture requirement: an
 * AudioContext cannot produce sound until the user has interacted with the
 * page. {@link #resumeAfterUserGesture()} is invoked from the first click/key
 * handler. Positional audio uses Web Audio's PannerNode.</p>
 */
public interface EaglerAudio {

	void init();

	/** Must be called from within a user gesture on browsers to unlock audio. */
	void resumeAfterUserGesture();

	/** Load and decode a resource; returns a handle playable later. */
	IAudioHandle createTrack(IAudioResource resource);

	void play(IAudioHandle handle, float volume, float pitch, boolean loop);

	void playPositional(IAudioHandle handle, float x, float y, float z,
			float volume, float pitch, boolean loop);

	void stop(IAudioHandle handle);

	void setMasterVolume(float volume);

	void pauseAll();

	void resumeAll();

	void release(IAudioHandle handle);

	void destroy();

	/** True when the platform can spatially (3D) position audio. */
	boolean supportsPositionalAudio();
}