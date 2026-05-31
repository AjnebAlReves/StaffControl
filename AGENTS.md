# StaffPlus / StaffControl

Monorepo con dos distribuciones independientes de StaffControl (plugin de moderación para Minecraft).

## Distribuciones

| Distribución | Servidores | Java | Módulos | JAR final |
|---|---|---|---|---|
| **Legacy** (`Staff+`) | 1.7 – 1.16 | Java 8 | StaffPlusAPI + StaffPlusCore + 16 NMS modules | `Staff+.jar` |
| **Modern** (`StaffControl`) | 1.17+ | Java 17+ | staff-api + staff-modern-core | `StaffControl.jar` |

Ambas comparten la misma API (`staff-api`, package `xyz.bt31.staffcontrol.api`). El legacy es mantenimiento-congelado (solo bugs). El moderno se desarrolla activamente contra Paper API.

## Build

### Legacy
```bash
bash install-dependencies.sh   # one-time: craftbukkit + NMS modules → .m2
mvn clean package              # StaffPlusCore/target/Staff+.jar
```

### Modern
```bash
mvn clean package -pl staff-api,staff-modern-core -am
# staff-modern-core/target/StaffControl.jar
```

### Full reactor (todo lo que el JDK soporte)
```bash
mvn clean package
```

## Módulos activos en reactor

| Módulo | Propósito | Compila con |
|---|---|---|
| `StaffPlusAPI` | API legacy (`net.shortninja.staffplus`) — **frozen** | Java 8 |
| `staff-api` | API compartida (`xyz.bt31.staffcontrol.api`) | Java 8 |
| `StaffPlusCore` | Core legacy — comentado, necesita craftbukkit | Java 8 |
| `v1_17_plus` | Adapter NMS-free legacy — comentado, necesita JDK 16+ | Java 16 |
| `staff-modern-core` | Core moderno Paper 1.17+ — comentado, necesita JDK 17+ | Java 17 |

## StaffControl (distribución moderna)

### Dependencias
- **Paper API 1.17+** (`io.papermc.paper:paper-api`, provided)
- **staff-api** (`xyz.bt31.staffcontrol:staff-api`, compile → shaded)
- **adventure-text-minimessage 4.17.0** (`net.kyori:adventure-text-minimessage`, compile → shaded)

### Stack tecnológico
- Paper API + Adventure (chat, action bar, componentes)
- MiniMessage para formato de texto con etiquetas legibles (`<red>`, `<gradient>`, `<click>`, etc.)
- Sin NMS, sin Netty, sin PacketListenerAPI
- Sin ServiceLoader, sin reflexión para versiones
- Shade plugin produce `StaffControl.jar` listo para soltar en `plugins/`

### Arquitectura
```
staff-modern-core/
├── StaffControlPlugin.java   ← extends JavaPlugin, entry point
├── StaffControl.java         ← implements IStaffControl
├── Options.java
├── PermissionsHandler.java
├── user/
│   ├── User.java
│   └── UserManager.java
├── lang/
│   ├── Lang.java             ← MiniMessage + messages-{locale}.yml
│   └── messages-en.yml       ← mensajes por defecto en formato MiniMessage
├── command/
│   ├── Command.java          ← base abstracta (permisos, player check, tab complete)
│   ├── VanishCommand.java    ← /vanish [player]
│   ├── FreezeCommand.java    ← /freeze <player>
│   ├── ReportCommand.java    ← /report <player> <reason>
│   ├── WarnCommand.java      ← /warn <player> <reason>
│   └── StaffCommand.java     ← /staff <reload|chat>
├── capabilities/
│   ├── ModernActionBarCapability.java
│   ├── ModernChatCapability.java
│   ├── ModernPlayerVisibilityCapability.java
│   └── ModernInventoryCapability.java
└── listener/
    ├── PlayerJoinListener.java
    ├── PlayerQuitListener.java
    └── ChatAlertListener.java
```

## Testing

No hay tests unitarios. Verificación solo build.

## CI

CircleCI: pendiente de configuración para la nueva estructura.

## Notas importantes

- El repositorio original se movió de `AjnebAlReves/StaffPlus` → `AjnebAlReves/StaffControl` (el remote se redirige automáticamente).
- La rama activa de desarrollo es `feat/v1_17-plus-module`.
- El workspace tiene JDK 11; **no se puede compilar** `staff-modern-core` (necesita JDK 17+) ni `v1_17_plus` (necesita JDK 16+) aquí. Solo compila `staff-api` + `StaffPlusAPI`.
- Los módulos legacy `v1_7_R1` – `v1_16_R2` están congelados. Se les removieron las clases `ProtocolProvider*` y `META-INF/services/net.shortninja.staffplus.server.compatibility.protocol.*`.
- StaffPlusCore (`StaffPlus.java`) usa `provider.supports()` en vez de `.equals()` y atrapa `ServiceConfigurationError`. No tocar.

## Convenciones

- `.gitignore` limpio (sin marcadores de merge conflict)
- Legacy: Java 8 (`-source 8 -target 8`). Moderno: Java 17+.
- No sombrear APIs de Bukkit/Spigot/Paper (scope `provided`).
- `staff-api` es Java 8 para que ambas distribuciones puedan usarlo.
- Todos los mensajes al jugador usan Adventure Components via Lang; nunca enviar Strings planas.
- Los templates de mensajes NO incluyen `<prefix>` inline; usar `Lang.sendWithPrefix()` que antepone el Component.
- Placeholders en mensajes: usar `Lang.target()`, `Lang.staff()`, `Lang.reason()`, etc. (TagResolvers de MiniMessage).
- Commands extienden `Command` (base) que implementa `CommandExecutor` + `TabCompleter`.
- Permisos definidos en `plugin.yml` con naming `staffcontrol.*`.
