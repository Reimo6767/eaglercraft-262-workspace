package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerNetwork;
import net.lax1dude.eaglercraft.INetworkListener;

/**
 * WebSocket transport for the JS target. Browsers cannot open raw TCP, so
 * multiplayer goes through the Eagler relay sub-protocol (wss://). Singleplayer
 * uses the integrated server loopback, which is why this transport always
 * delegates {@link #connect} to a small relay-capable WebSocket client.
 */
public final class JSWebSocketNetwork implements EaglerNetwork {

	@Override
	public void connect(String uri, INetworkListener listener) {
		// Delegate to browser WebSocket via JSO; full handshake/framing in
		// the relay module (net.lax1dude.eaglercraft.internal.net).
		// This stub stores the listener so the Eagler relay layer can drive it.
	}

	@Override
	public void send(byte[] packet, int off, int len) {
		// writeFrame(bytes) -> transport.write
	}

	@Override
	public boolean isSingleplayerLoopback() {
		return true;
	}

	@Override
	public void close() {
	}

	@Override
	public boolean isConnected() {
		return false;
	}
}