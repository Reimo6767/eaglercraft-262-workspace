package net.lax1dude.eaglercraft.internal.teavm;

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
 * Browser platform binding for the JS target. Each subsystem is a thin
 * adapter over browser/W3C APIs exposed through TeaVM JSO and @JSBody.
 */
public final class JSPlatform implements EaglerPlatform {

	private final EaglerSurface surface = new JSSurface();
	private final EaglerInput input = new JSInput();
	private final EaglerAudio audio = new JSAudio();
	private final EaglerNetwork network = new JSNetwork();
	private final EaglerStorage storage = new JSStorage();
	private final EaglerWindow window = new JSWindow();
	private final EaglerGPU gpu = new JSWebGL2GPU();
	private final EaglerClock clock = new JSClock();

	@Override
	public EaglerSurface surface() {
		return surface;
	}

	@Override
	public EaglerInput input() {
		return input;
	}

	@Override
	public EaglerAudio audio() {
		return audio;
	}

	@Override
	public EaglerNetwork network() {
		return network;
	}

	@Override
	public EaglerStorage storage() {
		return storage;
	}

	@Override
	public EaglerWindow window() {
		return window;
	}

	@Override
	public EaglerGPU gpu() {
		return gpu;
	}

	@Override
	public EaglerClock clock() {
		return clock;
	}

	@Override
	public String runtimeName() {
		return "teavm-javascript";
	}

	@Override
	public boolean isBrowserRuntime() {
		return true;
	}
}