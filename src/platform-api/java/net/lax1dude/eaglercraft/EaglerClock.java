package net.lax1dude.eaglercraft;

/**
 * Time source. Brick post-patch 1.8+ Minecraft wants real timers; the browser
 * uses high-resolution monotonic time (performance.now) converted to the
 * Minecraft tick/lowerInterval semantics. Never blocks.
 */
public interface EaglerClock {

	/** Monotonic milliseconds since runtime boot. */
	long nanotime();

	/** Monotonic milliseconds since runtime boot. */
	long millis();

	/** Sleep equivalent. On browsers this yields to the event loop (setTimeout). */
	void sleepInterval(long millis);

	/** Called once per frame to push a timestamp used by tick pacing. */
	void tick();

	/** Long, wall-clock time for stats (nullable safe). */
	String formattedWallTime();
}