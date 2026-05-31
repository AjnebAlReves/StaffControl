# Staff+ (`StaffPlus`)

Spigot/Bukkit moderation plugin supporting Minecraft 1.7–1.16. Java 8, Maven multi-module.

## Build

```bash
mvn clean package          # produces StaffPlusCore/target/Staff+.jar (shaded)
mvn clean install          # CI does this; also installs to local .m2
```

First-time builds need Spigot/CraftBukkit in local `.m2`. Either run [BuildTools](https://www.spigotmc.org/wiki/buildtools/) for the Bukkit version in `pom.xml` (`1.16.2-R0.1-SNAPSHOT`) or run:

```bash
bash install-dependencies.sh   # downloads old CraftBukkit versions (1.7–1.15)
```

Use `VersionBumper.sh` to bump all module versions at once:

```bash
bash VersionBumper.sh          # prompts for new version, runs `mvn versions:set -DgenerateBackupPoms=false`
```

## Modules

| Module | Purpose |
|---|---|
| `StaffPlusAPI` | Public API interfaces (`IStaffPlus`, `IUser`, etc.) |
| `StaffPlusCore` | Main plugin; depends on API + all version modules; shaded into `Staff+.jar` |
| `v1_7_R1` … `v1_16_R2` | Per-MC-version protocol compatibility (16 modules) |
| `v1_17_plus` | **NMS-free** module for Minecraft 1.17+ using Bukkit API + capability system |
| `StaffPlusBungee` | BungeeCord module — **commented out** of parent POM, not ready |

- `StaffPlusBungee` is excluded from the reactor build (`<!-->` in `<modules>`).

## Key Files

- `StaffPlusCore/src/main/resources/plugin.yml` — main class `net.shortninja.staffplus.StaffPlus`, soft-depends: `PacketListenerApi`, `PlaceholderAPI`
- `StaffPlusCore/src/main/java/net/shortninja/staffplus/StaffPlus.java` — JavaPlugin entrypoint; protocol classes loaded via `ServiceLoader<IProtocolProvider>` first, then reflection fallback from `net.shortninja.staffplus.server.compatibility.{version}.Protocol_{version}`, with a `v1_1x` package fallback

## Adding a new Minecraft version

### For pre-1.17 versions (with NMS):

No Java source changes needed. Only:
1. Create a new Maven module (e.g. `v1_17_R1`) copying an existing version module's structure
2. Add `<module>v1_17_R1</module>` to parent `pom.xml`
3. Add a `<dependency>` for it in `StaffPlusCore/pom.xml`
4. Add a `ProtocolProvider_{version}` class in the module implementing `IProtocolProvider` and registering it via `META-INF/services/net.shortninja.staffplus.server.compatibility.IProtocolProvider`
5. Ensure the Protocol class is named `Protocol_{version}` (e.g. `Protocol_v1_17_R1`) and placed in package `net.shortninja.staffplus.server.compatibility.{version}` (or `v1_1x` if sharing with other post-1.14 versions)

### For 1.17+ (NMS-free via capability system):

Copy the `v1_17_plus` module and update:
1. Copy `v1_17_plus` → `v1_18_plus`
2. Update `ProtocolProvider_{version}` version string (e.g. `v1_18_R1`)
3. Add module to parent `pom.xml` and `StaffPlusCore/pom.xml`
4. Override `maven.compiler.source/target` to Java 16+ (in module POM)
5. Capability implementations need no changes — they use pure Bukkit/Spigot API

At runtime, `StaffPlus.java` tries `ServiceLoader<IProtocolProvider>` first (matching by version string), then falls back to reflection on the Bukkit package name (`v1_17_R1`). No switch statement or import to update.

## Version compatibility modules

Each `v1_7_R1` … `v1_16_R2` module contains:
- `Protocol_{version}` — NMS-specific protocol implementation (craftbukkit `provided` scope)
- `ProtocolProvider_{version}` — ServiceLoader provider that registers the version string
- `PacketHandler_{version}` — Netty `ChannelDuplexHandler` for packet interception
- `PacketModifier_{version}` — optional packet modifier (newer versions only)
- `META-INF/services/net.shortninja.staffplus.server.compatibility.IProtocolProvider` — service descriptor

The `v1_17_plus` module uses a different approach — no NMS, pure Bukkit/Spigot API:
- `Protocol_v1_17_R1` — delegates to `VersionCapabilities` for version-sensitive operations
- `ProtocolProvider_v1_17_R1` — ServiceLoader provider
- `PacketHandler_v1_17_R1` — uses string-based class name matching (no NMS imports)
- `capabilities/` — thin adapters for action bar, visibility, chat, inventory
- `util/ComponentSerializer` — BungeeCord Chat API-based component builder

The shade plugin in `StaffPlusCore/pom.xml` uses `ServicesResourceTransformer` to merge all per-module service descriptors into one consolidated file in `Staff+.jar`.

## Testing

There are **no tests** (no JUnit or test dependency in any POM). Verification is build-only.

## CI

CircleCI (`ci/circleci: build`): `mvn clean install` on `circleci/openjdk:8-jdk`.

## Conventions & gotchas

- `.gitignore` has leftover merge-conflict markers (`<<<<<<< HEAD` / `>>>>>>>`). Clean them when touching the file.
- All modules target Java 8 (`maven.compiler.source/target = 1.8`). Do not use Java 9+ APIs. Exception: `v1_17_plus` targets Java 16+ (required by Spigot 1.17+).
- The shade plugin in `StaffPlusCore` excludes `netty-all`, `hamcrest`, `junit`, and META-INF signatures. Do not shade Spigot/Bukkit APIs (they are `provided` scope).
- Parent POM `<defaultGoal>clean package</defaultGoal>` — `mvn` with no goal runs `clean package`.
