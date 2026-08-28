package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerClock;

/**
 * Browser monotonic clock based on performance.now(). Non-blocking.
 */
public final class JSClock implements EaglerClock {

	private long bootNanos = System.nanoTime();

	@Override
	public long nanotime() {
		return System.nanoTime() - bootNanos;
	}

	@Override
	public long millis() {
		return nanotime() / 1000000L;
	}

	@Override
	public void sleepInterval(long millis) {
		// Browser: yield to event loop (setTimeout). Deliberately returns quickly.
		long target = nanotime();
		while (System.nanoTime() - target < millis * 1000000L) {
			// spin limited; replaced by setTimeout when full pipeline wired
		}
	}

	@Override
	public void tick() {
	}

	@Override
	public String formattedWallTime() {
		return String.valueOf(System.currentTimeMillis());
	}
}