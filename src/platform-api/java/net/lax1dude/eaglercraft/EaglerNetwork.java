package net.lax1dude.eaglercraft;

/**
 * Networking abstraction separating connection, framing, and transport.
 *
 * <p>Browsers cannot open raw TCP sockets, so the browser transport is a
 * WebSocket-based Eagler relay (wss://) speaking the Eagler sub-protocol.
 * The desktop debug transport uses raw sockets directly. Callers only ever
 * see: connect, send packet bytes, receive packet bytes, close.</p>
 */
public interface EaglerNetwork {

	/** Asynchronously opens a connection. Result delivered via {@code listener}. */
	void connect(String uri, INetworkListener listener);

	void send(byte[] packet, int off, int len);

	/** Raw transport kind. Browser singleplayer uses the "integrated" loopback. */
	boolean isSingleplayerLoopback();

	void close();

	boolean isConnected();
}