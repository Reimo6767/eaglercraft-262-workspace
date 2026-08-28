plugins {
	id("java")
}

allprojects {
	apply(plugin = "eclipse")

	repositories {
		mavenCentral()
	}

	plugins.withId("java") {
		java {
			toolchain {
				languageVersion = JavaLanguageVersion.of(17)
			}
		}
	}
}

// =====================================================================
// Root project: shared game + Eagler platform source, compiled once and
// consumed by every runtime target. This module holds ONLY the Eagler
// platform abstraction, the migration compatibility layer, and any
// open-source dependency guards. The proprietary decompiled Minecraft
// 26.2 source is NOT committed here; it is dropped in by the developer
// as documented input (see docs/source-input.md).
// =====================================================================

java {
	// Minecraft's patched sources use Java 11 target classes regardless of
	// toolchain; TeaVM consumes these at analysis time.
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

sourceSets {
	named("main") {
		java.srcDirs(
			"src/game/java",          // Minecraft 26.2 + Eagler patches (native code)
			"src/protocol-game/java", // protocol serialization
			"src/protocol-relay/java",// relay transport
			"src/platform-api/java"   // Eagler platform abstraction (common)
		)
	}
}

dependencies {
	implementation(libs.bundles.common)
}

tasks.withType<Jar> {
	entryCompression = ZipEntryCompression.STORED
	// TeaVM must never see the pure-Java platform-api in the game JAR; the
	// per-target platform implementation is provided by the target source set.
	fileTree("src/platform-api/java").visit {
		if (!isDirectory) {
			if (path.endsWith(".java")) {
				exclude(path.substring(0, path.length - 5) + ".class")
			}
		}
	}
}

// Empty placeholder so Gradle can always resolve the root output even before
// a developer has provided the proprietary source input.
tasks.named("jar").configure {
	dependsOn += "classes"
}