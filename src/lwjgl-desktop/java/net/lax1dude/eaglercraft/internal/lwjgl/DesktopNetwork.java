package net.lax1dude.eaglercraft.internal.lwjgl;

import net.lax1dude.eaglercraft.EaglerNetwork;
import net.lax1dude.eaglercraft.INetworkListener;

/** Desktop raw-socket transport. Debug quality. */
public final class DesktopNetwork implements EaglerNetwork {

	@Override public void connect(String uri, INetworkListener listener) { }
	@Override public void send(byte[] packet, int off, int len) { }
	@Override public boolean isSingleplayerLoopback() { return true; }
	@Override public void close() { }
	@Override public boolean isConnected() { return false; }
}