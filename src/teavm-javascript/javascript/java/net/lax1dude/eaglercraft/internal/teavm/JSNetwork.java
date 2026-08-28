package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerNetwork;
import net.lax1dude.eaglercraft.INetworkListener;

/** Alias binding for the JS target; centralizes transport choice. */
public final class JSNetwork implements EaglerNetwork {

	private final EaglerNetwork ws = new JSWebSocketNetwork();

	@Override
	public void connect(String uri, INetworkListener listener) {
		ws.connect(uri, listener);
	}

	@Override
	public void send(byte[] packet, int off, int len) {
		ws.send(packet, off, len);
	}

	@Override
	public boolean isSingleplayerLoopback() {
		return ws.isSingleplayerLoopback();
	}

	@Override
	public void close() {
		ws.close();
	}

	@Override
	public boolean isConnected() {
		return ws.isConnected();
	}
}