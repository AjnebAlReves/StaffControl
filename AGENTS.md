# Staff+ (`StaffPlus`)

Spigot/Bukkit moderation plugin supporting Minecraft 1.7–1.16. Java 8, Maven multi-module.

## Build

```bash
bash install-dependencies.sh   # one-time: installs craftbukkit + legacy version modules into .m2
mvn clean package              # produces StaffPlusCore/target/Staff+.jar (shaded)
```

First-time builds need Spigot/CraftBukkit in local `.m2`. Run `install-dependencies.sh` (or use [BuildTools](https://www.spigotmc.org/wiki/buildtools/) for individual Bukkit versions).

## Modules

| Module | Purpose | Status |
|---|---|---|
| `StaffPlusAPI` | Public API interfaces (`IStaffPlus`, `IUser`, etc.) | Active |
| `StaffPlusCore` | Main plugin; shaded into `Staff+.jar` | Active |
| `v1_17_plus` | **NMS-free** module for Minecraft 1.17+ (covers ALL future versions) | Active |
| `v1_7_R1` … `v1_16_R2` | Legacy per-version protocol compatibility (16 modules) | **FROZEN** – pre-built by `install-dependencies.sh`, never modified |

- `StaffPlusBungee` is excluded from the reactor build, not ready.

## Key Files

- `StaffPlusCore/src/main/resources/plugin.yml` — main class `net.shortninja.staffplus.StaffPlus`, soft-depends: `PacketListenerApi`, `PlaceholderAPI`
- `StaffPlusCore/src/main/java/net/shortninja/staffplus/StaffPlus.java` — JavaPlugin entrypoint; protocol classes loaded via `ServiceLoader<IProtocolProvider>` first, then reflection fallback from `net.shortninja.staffplus.server.compatibility.{version}.Protocol_{version}`, with a `v1_1x` package fallback

## Adding a new Minecraft version

### For pre-1.17 versions (with NMS):

**Not applicable – legacy modules are FROZEN.** They will not be updated for new versions.

### For 1.17+ (NMS-free via capability system):

**No new modules needed.** The `v1_17_plus` module handles ALL 1.17+ versions. When a new Minecraft version releases:
1. Update the version string in `ProtocolProvider_v1_17_R1.getVersion()` (e.g. change `"v1_17_R1"` to `"v1_18_R1"`)
2. Optionally rename `Protocol_v1_17_R1` → `Protocol_v1_18_R1` if the class name must match the version string
3. Capability implementations need no changes — they use pure Bukkit/Spigot API

At runtime, `StaffPlus.java` tries `ServiceLoader<IProtocolProvider>` first (matching by version string), then falls back to reflection on the Bukkit package name (`v1_17_R1`). No switch statement or import to update.

## Version compatibility modules

### Legacy (v1_7_R1 … v1_16_R2) — FROZEN

Each legacy module contains:
- `Protocol_{version}` — NMS-specific protocol implementation (craftbukkit `provided` scope)
- `ProtocolProvider_{version}` — ServiceLoader provider that registers the version string
- `PacketHandler_{version}` — Netty `ChannelDuplexHandler` for packet interception
- `PacketModifier_{version}` — optional packet modifier (newer versions only)
- `META-INF/services/net.shortninja.staffplus.server.compatibility.IProtocolProvider` — service descriptor

These modules are pre-built by `install-dependencies.sh` and installed to `.m2`. They are **never modified**. StaffPlusCore depends on them via Maven coordinates.

### Modern (v1_17_plus) — Active

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

CircleCI (`ci/circleci: build`): `bash install-dependencies.sh && mvn clean install` on `circleci/openjdk:8-jdk`.

## Conventions & gotchas

- `.gitignore` has leftover merge-conflict markers (`<<<<<<< HEAD` / `>>>>>>>`). Clean them when touching the file.
- All modules target Java 8 (`maven.compiler.source/target = 1.8`). Do not use Java 9+ APIs. Exception: `v1_17_plus` targets Java 16+ (required by Spigot 1.17+).
- The shade plugin in `StaffPlusCore` excludes `netty-all`, `hamcrest`, `junit`, and META-INF signatures. Do not shade Spigot/Bukkit APIs (they are `provided` scope).
- Parent POM `<defaultGoal>clean package</defaultGoal>` — `mvn` with no goal runs `clean package`.
