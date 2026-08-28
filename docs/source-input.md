# Legitimate Source Input

This repository does **not** contain any decompiled Minecraft source, and never
will. The Eaglercraft model (which this workspace follows) is:

> You supply a **legitimate local copy** of the Minecraft client JAR you already
> own a license for. The workspace decomposes it and applies Eagler patch sets
> to make it browser-compatible, then TeaVM compiles the result.

## What you must provide

A licensed, locally-downloaded **Minecraft Java Edition 26.2** client JAR
(`client-<sha>.jar`). Because the game is distributed proprietary, we cannot
(and will not) host or fetch it for you.

## What the workspace does with it

1. **Decompile** the client JAR with your toolchain of choice (MCP/Vineflower +
   mappings for the 26.2 source/names). We do not redistribute these tools'
   configs here — see `reference/` for the tooling placeholders.
2. **Drop the deobfuscated Java** into `port-src/minecraft-26.2/`.
3. Run the **Eagler patch** application (scripts in `scripts/`) to replace
   LWJGL/desktop-only calls with the `net.lax1dude.eaglercraft.*` runtime path.
4. Gradle compiles the patched source against the Eagler platform-api, then
   TeaVM emits browser JavaScript / WASM.

## Layout of injected source

Expected structure under `port-src/minecraft-26.2/`:

```
port-src/minecraft-26.2/
  src/main/java/...      <-- decompiled 26.2 java
  resources/...          <-- assets pulled from the jar (or supplied separately)
```

## Integrity note

Version integrity is verified by comparing against the 26.2 reference:
deobfuscated mappings version, protocol identifier, block/item registries, and
resource formats. The workspace never "renames" an older client. If the 
26.2 source is not provided, the JS/WASM targets compile the Eagler runtime +
demo boot only (see `docs/bootstrap.md`) — that is a **pipeline proof**, not a
fake full-client, and is labelled as such in the UI.

## Reproducible build hand-off

```bash
# 1. provide legitimate input
cp ~/path/to/client-26.2.jar reference/build-inputs/
# 2. decompile + drop source into port-src/
# 3. build
./gradlew :target_teavm_javascript:buildEaglerJS
# 4. serve
python3 -m http.server 8080 --directory dist/client
```

No manual post-steps. No files copied from another machine.