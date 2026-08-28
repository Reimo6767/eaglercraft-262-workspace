package net.lax1dude.eaglercraft;

/**
 * Asynchronous network event sink. All methods are called on whatever thread
 * the transport owns; on browsers this is the main/event loop thread so the
 * handler must not block.
 */
public interface INetworkListener {

	void onOpen();

	/** A complete framed packet. Copy before returning — the buffer may be reused. */
	void onPacket(byte[] data, int off, int len);

	void onError(String message);

	void onClose(int code, String reason);
}