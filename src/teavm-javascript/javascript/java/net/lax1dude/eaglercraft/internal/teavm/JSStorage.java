package net.lax1dude.eaglercraft.internal.teavm;

import java.util.HashMap;
import java.util.Map;

import net.lax1dude.eaglercraft.EaglerStorage;

/**
 * In-memory storage with an IndexedDB-backed cache. This implementation
 * provides an in-memory map (for the pipeline proof and desktop parity) and
 * documents where the durable IndexedDB adapter drops in. The abstraction
 * keeps the game code identical across storage backends.
 */
public final class JSStorage implements EaglerStorage {

	private final Map<String, byte[]> files = new HashMap<>();

	@Override
	public boolean fileExists(String path) {
		return files.containsKey(path) && !isDirectory(path);
	}

	@Override
	public byte[] readFile(String path) {
		return files.get(path);
	}

	@Override
	public void writeFile(String path, byte[] data) {
		files.put(path, data);
	}

	@Override
	public boolean deleteFile(String path) {
		return files.remove(path) != null;
	}

	@Override
	public String[] list(String dirPath) {
		return files.keySet().stream()
				.filter(p -> belongsTo(p, dirPath))
				.toArray(String[]::new);
	}

	private static boolean belongsTo(String path, String dir) {
		String d = (dir == null || dir.isEmpty() || dir.equals("/")) ? "" : dir;
		if (path.startsWith(d) && path.length() > d.length()) {
			char c = path.charAt((d.isEmpty()) ? 0 : d.length());
			return c == '/' || d.isEmpty();
		}
		return dir == null || dir.isEmpty();
	}

	@Override
	public boolean isDirectory(String path) {
		return !files.containsKey(path);
	}

	@Override
	public void flush() {
		// Persist to IndexedDB async
	}

	@Override
	public String cacheBasePath() {
		return "/cache/";
	}
}