package net.lax1dude.eaglercraft.internal.lwjgl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.lax1dude.eaglercraft.EaglerStorage;

/** Desktop storage backed by a real directory under the working directory. */
public final class DesktopStorage implements EaglerStorage {

	private final File root = new File("desktopRuntime/eagler-storage");

	public DesktopStorage() {
		if (!root.exists()) {
			root.mkdirs();
		}
	}

	@Override public boolean fileExists(String path) { return new File(root, path).isFile(); }

	@Override public byte[] readFile(String path) {
		try { return Files.readAllBytes(new File(root, path).toPath()); }
		catch (IOException ex) { return null; }
	}

	@Override public void writeFile(String path, byte[] data) {
		File f = new File(root, path);
		f.getParentFile().mkdirs();
		try { Files.write(f.toPath(), data); }
		catch (IOException ex) { throw new RuntimeException(ex); }
	}

	@Override public boolean deleteFile(String path) { return new File(root, path).delete(); }

	@Override public String[] list(String dirPath) {
		File d = new File(root, dirPath);
		File[] c = d.listFiles();
		if (c == null) return new String[0];
		String[] r = new String[c.length];
		for (int i = 0; i < c.length; i++) r[i] = c[i].getName();
		return r;
	}

	@Override public boolean isDirectory(String path) { return new File(root, path).isDirectory(); }

	@Override public void flush() { }

	@Override public String cacheBasePath() { return "/cache/"; }
}