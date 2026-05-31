# StaffPlus / StaffControl

Monorepo containing two independent distributions of the StaffControl moderation platform for Minecraft servers.

## Distributions

| Distribution              | Server Versions | Java     | Modules                                        | Final Artifact     |
| ------------------------- | --------------- | -------- | ---------------------------------------------- | ------------------ |
| **Staff+ (Legacy)**       | 1.7 – 1.16      | Java 8   | StaffPlusAPI + StaffPlusCore + version modules | `Staff+.jar`       |
| **StaffControl (Modern)** | 1.17+           | Java 17+ | staff-api + staff-modern-core                  | `StaffControl.jar` |

Both distributions share the same public API concepts, but target different Minecraft generations.

* **Legacy** is feature-frozen and only receives bug fixes.
* **Modern** is actively developed against the Paper API.

---

## Building

### Legacy

```bash
bash install-dependencies.sh
mvn clean package
```

Produces:

```text
StaffPlusCore/target/Staff+.jar
```

### Modern

```bash
mvn clean package -pl staff-api,staff-modern-core -am
```

Produces:

```text
staff-modern-core/target/StaffControl.jar
```

### Full Reactor

```bash
mvn clean package
```

Builds all modules supported by the current JDK.

---

## Active Modules

| Module            | Description                      | Java |
| ----------------- | -------------------------------- | ---- |
| StaffPlusAPI      | Legacy public API (frozen)       | 8    |
| staff-api         | Shared API                       | 8    |
| StaffPlusCore     | Legacy implementation            | 8    |
| v1_17_plus        | Transitional compatibility layer | 16   |
| staff-modern-core | Modern Paper implementation      | 17+  |

---

## Modern Architecture

### Dependencies

* Paper API (provided)
* staff-api
* Adventure MiniMessage

### Design Goals

* No NMS
* No packet manipulation
* No ProtocolLib
* No PacketListenerAPI
* No runtime version reflection
* Adventure-first messaging
* Paper-first development

### Core Structure

```text
staff-modern-core/
├── StaffControlPlugin.java
├── StaffControl.java
├── command/
├── listener/
├── user/
├── lang/
├── capabilities/
└── permissions/
```

---

## Testing

Currently there are no automated tests.

Validation is performed through successful builds and runtime verification.

---

## Continuous Integration

CircleCI migration is currently in progress.

---

## Development Notes

* Repository migrated from `AjnebAlReves/StaffPlus` to `AjnebAlReves/StaffControl`.
* Active development branch: `feat/v1_17-plus-module`.
* Legacy compatibility modules (`v1_7_R1` through `v1_16_R2`) are maintenance-only.
* `staff-api` remains Java 8 compatible to support both distributions.

---

## Coding Conventions

### Messaging

* Use Adventure Components exclusively.
* Never send raw Strings directly to players.
* Use `Lang.sendWithPrefix()` for prefixed messages.
* Use TagResolvers for placeholders.

### Commands

* Commands must extend the shared `Command` base class.
* Permissions follow the `staffcontrol.*` namespace.

### Dependencies

* Bukkit, Spigot and Paper APIs must remain `provided`.
* Do not shade server APIs into plugin artifacts.

### Java Versions

| Component  | Java |
| ---------- | ---- |
| Legacy     | 8    |
| Shared API | 8    |
| Modern     | 17+  |
