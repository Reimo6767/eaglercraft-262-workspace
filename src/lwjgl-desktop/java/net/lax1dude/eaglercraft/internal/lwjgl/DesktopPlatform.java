package net.lax1dude.eaglercraft.internal.lwjgl;

import net.lax1dude.eaglercraft.EaglerAudio;
import net.lax1dude.eaglercraft.EaglerClock;
import net.lax1dude.eaglercraft.EaglerGPU;
import net.lax1dude.eaglercraft.EaglerInput;
import net.lax1dude.eaglercraft.EaglerNetwork;
import net.lax1dude.eaglercraft.EaglerPlatform;
import net.lax1dude.eaglercraft.EaglerStorage;
import net.lax1dude.eaglercraft.EaglerSurface;
import net.lax1dude.eaglercraft.EaglerWindow;

/**
 * Desktop (LWJGL / native) platform binding used for fast iterative debugging.
 *
 * <p>IMPORTANT: desktop success is NOT browser success. This runtime exists only
 * to debug rendering/gameplay logic without the TeaVM round-trip. The browser
 * targets remain the source of truth for final acceptance.</p>
 */
public final class DesktopPlatform implements EaglerPlatform {

	private final EaglerSurface surface = new DesktopSurface();
	private final EaglerInput input = new DesktopInput();
	private final EaglerAudio audio = new DesktopAudio();
	private final EaglerNetwork network = new DesktopNetwork();
	private final EaglerStorage storage = new DesktopStorage();
	private final EaglerWindow window = new DesktopWindow();
	private final EaglerGPU gpu = new DesktopGPU();
	private final EaglerClock clock = new DesktopClock();

	@Override public EaglerSurface surface() { return surface; }
	@Override public EaglerInput input() { return input; }
	@Override public EaglerAudio audio() { return audio; }
	@Override public EaglerNetwork network() { return network; }
	@Override public EaglerStorage storage() { return storage; }
	@Override public EaglerWindow window() { return window; }
	@Override public EaglerGPU gpu() { return gpu; }
	@Override public EaglerClock clock() { return clock; }

	@Override public String runtimeName() { return "lwjgl-desktop"; }
	@Override public boolean isBrowserRuntime() { return false; }
}