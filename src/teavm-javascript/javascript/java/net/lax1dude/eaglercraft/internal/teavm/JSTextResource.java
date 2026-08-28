package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.IAudioResource;

/** Simple in-memory audio resource wrapper, produced by the asset loader. */
public final class JSTextResource implements IAudioResource {

	private final byte[] data;
	private final String mime;

	public JSTextResource(byte[] data, String mime) {
		this.data = data;
		this.mime = mime;
	}

	@Override
	public byte[] data() {
		return data;
	}

	@Override
	public String mimeType() {
		return mime;
	}
}