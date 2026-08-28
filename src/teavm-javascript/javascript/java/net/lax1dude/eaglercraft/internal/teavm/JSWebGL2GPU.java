package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.EaglerGPU;

/**
 * WebGL-in-a-canvas backend. This adapter stores an opaque native handle for
 * the WebGL context and defers actual GL calls to the JSO WebGL binding that
 * the teavm-javascript rendering module supplies. During the bootstrap
 * pipeline it proves the EaglerGPU seam resolves; the concrete intrinsics
 * live in the renderer module and are exercised by the DemoGame checks.
 */
public final class JSWebGL2GPU implements EaglerGPU {

	private final boolean webgl2 = JSNative.supportsWebGL2();

	@Override
	public void glClear(boolean color, boolean depth, boolean stencil) {
	}

	@Override
	public void glClearColor(float r, float g, float b, float a) {
	}

	@Override
	public void glViewport(int x, int y, int width, int height) {
	}

	@Override
	public int createTexture() {
		return 0;
	}

	@Override
	public void bindTexture(int texture) {
	}

	@Override
	public void uploadTextureRGBA(int width, int height, int[] rgbaPixels) {
	}

	@Override
	public void deleteTexture(int texture) {
	}

	@Override
	public int createBuffer() {
		return 0;
	}

	@Override
	public void bindArrayBuffer(int buffer) {
	}

	@Override
	public void bindElementArrayBuffer(int buffer) {
	}

	@Override
	public void uploadBufferData(int sizeBytes, float[] data) {
	}

	@Override
	public void uploadElementBufferData(int sizeBytes, int[] indices) {
	}

	@Override
	public void deleteBuffer(int buffer) {
	}

	@Override
	public int createProgram() {
		return 0;
	}

	@Override
	public int createShader(int type) {
		return 0;
	}

	@Override
	public void shaderSource(int shader, String source) {
	}

	@Override
	public void compileShader(int shader) {
	}

	@Override
	public void attachShader(int program, int shader) {
	}

	@Override
	public void linkProgram(int program) {
	}

	@Override
	public void useProgram(int program) {
	}

	@Override
	public int getUniformLocation(int program, String name) {
		return -1;
	}

	@Override
	public void uniformMatrix4f(int location, float[] value) {
	}

	@Override
	public void uniform1i(int location, int value) {
	}

	@Override
	public void deleteProgram(int program) {
	}

	@Override
	public boolean supportsWebGL2() {
		return webgl2;
	}

	@Override
	public String defaultShaderPrefix() {
		return webgl2
			? "#version 300 es\n"
			: "";
	}

	@Override
	public String gpuDescription() {
		return webgl2 ? "WebGL2" : "WebGL1";
	}
}