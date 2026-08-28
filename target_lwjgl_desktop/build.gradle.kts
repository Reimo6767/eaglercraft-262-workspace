plugins {
	id("java")
	id("application")
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

sourceSets {
	named("main") {
		java.srcDirs(
			"../src/game/java",                       // Minecraft 26.2 + Eagler patches
			"../src/protocol-game/java",
			"../src/protocol-relay/java",
			"../src/platform-api/java",               // Eagler abstraction (common)
			"../src/lwjgl-desktop/java"               // desktop (LWJGL) backends
		)
	}
}

dependencies {
	implementation(files("../desktopRuntime"))                        // bundled desktop libs/jars
	implementation(libs.bundles.common)
}

application {
	mainClass.set("net.lax1dude.eaglercraft.internal.lwjgl.MainClass")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
	options.forkOptions.memoryMaximumSize = "2g"
}

tasks.register<JavaExec>("runDesktop") {
	group = "eagler"
	description = "Run the desktop LWJGL debug runtime."
	classpath = sourceSets["main"].runtimeClasspath
	mainClass.set("net.lax1dude.eaglercraft.internal.lwjgl.MainClass")
}

tasks.named("build").configure {
	dependsOn("classes")
}