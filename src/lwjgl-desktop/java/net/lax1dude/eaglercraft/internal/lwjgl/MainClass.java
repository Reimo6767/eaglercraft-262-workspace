package net.lax1dude.eaglercraft.internal.lwjgl;

import net.lax1dude.eaglercraft.EaglerPlatform;
import net.lax1dude.eaglercraft.EaglerRuntime;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Desktop debug entry point. Maps the same EaglerRuntime seam as the browser
 * entry point so that Minecraft-side code is identical regardless of target.
 */
public final class MainClass {

	private MainClass() {
	}

	public static void main(String[] args) {
		EaglerPlatform platform = new DesktopPlatform();
		EaglerRuntime.bind(platform);

		System.out.println("Eaglercraft 26.2 desktop runtime bound: "
				+ EaglerRuntime.runtimeNameSafe());

		SwingUtilities.invokeLater(MainClass::openWindow);
	}

	private static void openWindow() {
		JFrame frame = new JFrame("Eaglercraft 26.2 - desktop debug runtime");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280, 720);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}