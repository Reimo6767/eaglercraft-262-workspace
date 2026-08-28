package net.lax1dude.eaglercraft.internal.lwjgl;

import net.lax1dude.eaglercraft.EaglerClock;

public final class DesktopClock implements EaglerClock {
	private final long boot = System.nanoTime();
	@Override public long nanotime() { return System.nanoTime() - boot; }
	@Override public long millis() { return nanotime() / 1000000L; }
	@Override public void sleepInterval(long millis) {
		try { Thread.sleep(millis); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
	}
	@Override public void tick() { }
	@Override public String formattedWallTime() { return String.valueOf(System.currentTimeMillis()); }
}