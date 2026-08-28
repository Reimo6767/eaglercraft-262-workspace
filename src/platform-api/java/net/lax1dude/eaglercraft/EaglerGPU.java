package net.lax1dude.eaglercraft;

/**
 * GPU abstraction. On browser targets this is backed by WebGL2 (and degrading
 * to WebGL1 where unavailable); on desktop it is backed by LWJGL/OpenGL ES.
 *
 * <p>The API surface intentionally mirrors the small subset that Minecraft's
 * renderer needs and that both WebGL and desktop can satisfy, keeping the
 * two implementations thin adapters over a common backend while letting the
 * renderer use textures, buffers, programs, and framebuffers uniformly.</p>
 */
public interface EaglerGPU {

	void glClear(boolean color, boolean depth, boolean stencil);

	void glClearColor(float r, float g, float b, float a);

	void glViewport(int x, int y, int width, int height);

	int createTexture();

	void bindTexture(int texture);

	void uploadTextureRGBA(int width, int height, int[] rgbaPixels);

	void deleteTexture(int texture);

	int createBuffer();

	void bindArrayBuffer(int buffer);

	void bindElementArrayBuffer(int buffer);

	void uploadBufferData(int sizeBytes, float[] data);

	void uploadElementBufferData(int sizeBytes, int[] indices);

	void deleteBuffer(int buffer);

	int createProgram();

	int createShader(int type);

	void shaderSource(int shader, String source);

	void compileShader(int shader);

	void attachShader(int program, int shader);

	void linkProgram(int program);

	void useProgram(int program);

	int getUniformLocation(int program, String name);

	void uniformMatrix4f(int location, float[] value);

	void uniform1i(int location, int value);

	void deleteProgram(int program);

	/** Returns true when the backing API supports WebGL2-style unclamped float buffers. */
	boolean supportsWebGL2();

	String defaultShaderPrefix();

	/** Human-readable GPU/vendor string for diagnostics. */
	String gpuDescription();
}