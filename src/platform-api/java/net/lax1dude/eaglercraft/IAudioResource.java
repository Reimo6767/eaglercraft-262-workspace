package net.lax1dude.eaglercraft;

/** Source bytes of an audio asset (decoded by the backend). */
public interface IAudioResource {
	/** Raw PCM/OGG/WAV bytes as delivered by the asset pipeline. */
	byte[] data();
	String mimeType();
}