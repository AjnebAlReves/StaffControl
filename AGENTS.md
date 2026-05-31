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

### Stack tecnológico
- Paper API + Adventure (chat, action bar, componentes)
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
└── capabilities/
    ├── ModernActionBarCapability.java
    ├── ModernChatCapability.java
    ├── ModernPlayerVisibilityCapability.java
    └── ModernInventoryCapability.java
```

## Testing

No hay tests unitarios. Verificación solo build.

## CI

CircleCI: pendiente de configuración para la nueva estructura.

## Convenciones

- `.gitignore` limpio (sin marcadores de merge conflict)
- Legacy: Java 8 (`-source 8 -target 8`). Moderno: Java 17+.
- No sombrear APIs de Bukkit/Spigot/Paper (scope `provided`).
- `staff-api` es Java 8 para que ambas distribuciones puedan usarlo.
