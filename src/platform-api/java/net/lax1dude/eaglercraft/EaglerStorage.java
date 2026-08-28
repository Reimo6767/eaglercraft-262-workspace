package net.lax1dude.eaglercraft;

/**
 * Persistent storage abstraction.
 *
 * <p>Browser runtime stores worlds/settings/caches in IndexedDB;
 * desktop runtime uses a real directory. The abstraction presents a small
 * virtual-file-system so the game can keep its existing {@code File}-style
 * mental model (paths, list, read/write) without touching browser APIs.</p>
 */
public interface EaglerStorage {

	boolean fileExists(String path);

	byte[] readFile(String path);

	void writeFile(String path, byte[] data);

	boolean deleteFile(String path);

	String[] list(String dirPath);

	boolean isDirectory(String path);

	/** Persist any buffered writes immediately (IndexedDB tx flush, fsync on desktop). */
	void flush();

	/** Long-lived handle used to cache remote assets (textures, sounds). */
	String cacheBasePath();
}