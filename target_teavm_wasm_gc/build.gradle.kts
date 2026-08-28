import org.teavm.gradle.api.OptimizationLevel
import org.teavm.gradle.api.WasmDebugInfoLocation
import org.teavm.gradle.api.WasmDebugInfoLevel

plugins {
	id("java")
	id("org.teavm") version "0.12.1-EAGLER-R3"

	id("com.resentclient.oss.eaglercraft.build") version "0.0.0"
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
			"../src/teavm-wasm-gc/wasm-gc-teavm/java" // WASM-GC bootstrap + backends
		)
		resources.srcDirs(
			"../src/teavm-wasm-gc/resources"
		)
	}
}

repositories {
	maven {
		name = "eagler-teavm"
		url = uri("https://eaglercraft-teavm-fork.github.io/maven/")
	}
}

dependencies {
	teavm(teavm.libs.jso)
	teavm(teavm.libs.jsoApis)
	compileOnly("org.teavm:teavm-core:0.12.1-EAGLER-R3") // workaround for a few hacks
	implementation(libs.jorbis)
	implementation(libs.bundles.common)
}

val wasmFolder = "javascript"
val wasmOutputFileName = "classes.wasm"

teavm.wasmGC {
	targetFileName = "../" + wasmOutputFileName
	optimization = OptimizationLevel.BALANCED
	outOfProcess = false
	fastGlobalAnalysis = false
	processMemory = 512
	mainClass = "net.lax1dude.eaglercraft.internal.wasm_gc_teavm.MainClass"
	outputDir = file(wasmFolder)
	properties = mapOf("java.util.TimeZone.autodetect" to "true")
	debugInformation = true
	debugInfoLocation = WasmDebugInfoLocation.EXTERNAL
	debugInfoLevel = WasmDebugInfoLevel.DEOBFUSCATION
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}

// ---------------------------------------------------------------------
// WASM-GC viability (honest status, see docs/wasm-gc.md)
//
// TeaVM WASM-GC *can* compile the pure-Java core to classes.wasm here, but a
// runnable browser client additionally requires a large JS runtime harness
// (WebGL/WebSocket/IndexedDB JSO bridges), a Closure-Compiler bundle step,
// the Eagler `MakeWasmClientBundle` packaging, and WebAssembly JS Promise
// Integration (JSPI) + --experimental-wasm-gc in the browser. Those artifacts
// are not present in this bootstrap workspace, so WASM-GC is *not* yet a
// shippable client target. The JavaScript target is the supported path.
// Producing a fake `.wasm` that does not actually drive the client is
// contrary to the porting rules, so we deliberately do not do that.
// ---------------------------------------------------------------------

tasks.register("buildEaglerWASM") {
	group = "eagler"
	description = "Build the WASM-GC bytecode core (experimental; see docs/wasm-gc.md)"
	dependsOn("generateWasmGC")
}