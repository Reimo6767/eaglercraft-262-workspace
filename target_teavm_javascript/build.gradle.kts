import com.resentclient.oss.eaglercraft.build.impl.js
import org.teavm.gradle.api.OptimizationLevel
import org.teavm.gradle.tasks.GenerateJavaScriptTask

buildscript {
	dependencies {
		classpath(files("../reference-buildtools/teavmc-classpath/resources"))
	}
}

plugins {
	id("java")
	id("org.teavm") version "0.9.2"

	id("com.resentclient.oss.eaglercraft.build") version "0.0.0"
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

sourceSets {
	named("main") {
		java.srcDirs(
			"../src/game/java",                  // Minecraft 26.2 + Eagler patches (if provided)
			"../src/protocol-game/java",
			"../src/protocol-relay/java",
			"../src/platform-api/java",          // Eagler abstraction (common)
			"../src/teavm-javascript/javascript/java",
			"../src/teavm-javascript/teavm-boot/java"
		)
		resources.srcDirs(
			"../src/teavm-javascript/resources"
		)
	}
}

dependencies {
	teavm(teavm.libs.jso)
	teavm(teavm.libs.jsoApis)
	compileOnly("org.teavm:teavm-core:0.9.2") // workaround for a few hacks
	implementation(libs.jorbis)
	implementation(libs.bundles.common)
}

repositories {
	maven {
		name = "eagler-teavm"
		url = uri("https://eaglercraft-teavm-fork.github.io/maven/")
	}
}

val jsFolder = "javascript"
val jsFileName = "classes.js"

teavm.js {
	obfuscated = true
	sourceMap = true
	targetFileName = "../$jsFileName"
	optimization = OptimizationLevel.BALANCED // AGGRESSIVE for release
	outOfProcess = false
	fastGlobalAnalysis = false
	processMemory = 512
	entryPointName.set("main")
	mainClass = "net.lax1dude.eaglercraft.internal.teavm.MainClass"
	outputDir = file(jsFolder)
	properties = mapOf("java.util.TimeZone.autodetect" to "true")
	debugInformation = false
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
	// TeaVM consumes bytecode directly; we compile against the shared jar +
	// per-target sources. Ensure the compiled classes land where TeaVM looks.
	if (name == "compileJava") {
		options.compilerArgs.add("-proc:none")
	}
}

tasks.named<GenerateJavaScriptTask>("generateJavaScript") {
	doLast {
		val phile = file("$jsFolder/$jsFileName")
		if (!phile.exists()) return@doLast
		var dest = phile.readText()
		// ES6 shim safety (required by some older browsers and OGG fallback).
		val i = dest.substring(0, dest.indexOf("=\$rt_globals.Symbol('jsoClass');")).lastIndexOf("let ")
		dest = dest.substring(0, i) + "var" + dest.substring(i + 3)
		val j = dest.indexOf("function(\$rt_globals,\$rt_exports){")
		dest = dest.substring(0, j + 34) + "\n" +
			file("../reference-buildtools/ES6ShimScript.txt").readText() + "\n" +
			dest.substring(j + 34)
		phile.writeText(dest)
	}
}

// Friendly task name for the JS build.
tasks.register("buildEaglerJS") {
	group = "eagler"
	description = "Build the production JavaScript browser client (TeaVM)."
	dependsOn("generateJavaScript")
}

// Assemble a complete runnable client directory (classes.js + index.html)
// so developers never hand-assemble files. Output: dist/client
val packageClient = tasks.register<Copy>("packageClient") {
	group = "eagler"
	description = "Assemble a self-contained runnable client under dist/client."
	dependsOn("generateJavaScript")
	from("javascript/classes.js")
	from("javascript/classes.js.map")
	from("../src/teavm-javascript/resources/index.html")
	from("../src/teavm-javascript/resources") {
		exclude("**/index.html")
	}
	into("$projectDir/../dist/client")
}

tasks.register("stageClient") {
	group = "eagler"
	description = "Alias for packaging the runnable client."
	dependsOn(packageClient)
}