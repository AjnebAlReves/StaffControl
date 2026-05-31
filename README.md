# StaffControl

A modern moderation and staff management plugin for Minecraft servers.

StaffControl provides staff mode, vanish, reports, staff chat, player inspection tools, ticket management, alerts, and other moderation utilities through a unified and extensible platform.

Originally based on the Staff+ project, StaffControl has been modernized with a cleaner architecture, improved version compatibility, and a focus on long-term maintainability.

---

## Features

* Staff Mode
* Vanish System
* Staff Chat
* Reports & Tickets
* Player Examination Tools
* Teleportation Utilities
* Staff Counter & Online Monitoring
* Configurable Alerts
* Multi-language Support
* Extensive Configuration Options
* Modular Architecture
* Multi-Version Compatibility

## Supported Versions

StaffControl includes compatibility layers for multiple Minecraft versions.

Current repository support:

| Distribution | Versions | Java | JAR |
|---|---|---|---|
| **Staff+** (legacy) | 1.7.x – 1.16.x | 8 | `Staff+.jar` |
| **StaffControl** (moderno) | 1.17+ | 17+ | `StaffControl.jar` |

Modern compatibility work is developed on the `feat/v1_17-plus-module` branch.

## Project Structure

| Module | Description | JDK |
|---|---|---|
| `StaffPlusAPI` | Legacy API (`net.shortninja.staffplus`) — frozen | 8 |
| `staff-api` | Shared API (`xyz.bt31.staffcontrol.api`) | 8 |
| `StaffPlusCore` | Legacy core — needs CraftBukkit, frozen | 8 |
| `v1_17_plus` | NMS-free adapter — frozen | 16 |
| `staff-modern-core` | Modern core (Paper 1.17+) | 17+ |

## Building

### Requirements

* Java 8+ (legacy) or Java 17+ (modern)
* Maven

### Modern distribution (StaffControl)

```bash
mvn clean package -pl staff-api,staff-modern-core -am
```

The final shaded jar will be at:

```text
staff-modern-core/target/StaffControl.jar
```

### Legacy distribution (Staff+)

```bash
bash install-dependencies.sh   # one-time only
mvn clean package
```

The final shaded jar will be at:

```text
StaffPlusCore/target/Staff+.jar
```

### First-Time Setup (legacy only)

Some legacy Spigot/CraftBukkit artifacts are not available from public Maven repositories.

Install dependencies using:

```bash
bash install-dependencies.sh
```

or build the required Spigot version locally using BuildTools.

## Quick Start (StaffControl)

1. Drop `StaffControl.jar` into `plugins/`
2. Restart the server
3. Configure `plugins/StaffControl/config.yml`

### Permissions

| Permission | Description | Default |
|---|---|---|
| `staffcontrol.*` | All permissions | op |
| `staffcontrol.vanish` | Use `/vanish` | op |
| `staffcontrol.freeze` | Use `/freeze` | op |
| `staffcontrol.report` | Use `/report` | true |
| `staffcontrol.warn` | Use `/warn` | op |
| `staffcontrol.staff` | Use `/staff` | op |
| `staffcontrol.reload` | Reload config | op |
| `staffcontrol.alerts` | Receive alerts | op |
| `staffcontrol.reports.receive` | Receive report notifications | op |

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test your changes
5. Open a Pull Request

## Reporting Issues

If you encounter a bug or have a feature request, please open an issue in the GitHub issue tracker.

## License

This project is released under the terms of the included LICENSE file.
