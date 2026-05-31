# StaffControl / Staff+

Monorepo con dos distribuciones independientes de un plugin de moderación para Minecraft.

| Distribución | Servidores | Java | JAR final |
|---|---|---|---|
| **StaffControl** (moderno) | 1.17+ | 17+ | `StaffControl.jar` |
| **Staff+** (legacy) | 1.7 – 1.16 | 8 | `Staff+.jar` |

Ambas comparten la API `staff-api` (`xyz.bt31.staffcontrol.api`). La rama activa de desarrollo es `feat/v1_17-plus-module`.

---

## StaffControl (distribución moderna)

Plugin de moderación para servidores Paper 1.17+ sin NMS, sin reflexión de versiones, basado en Paper API + Adventure.

### Stack tecnológico
- **Paper API 1.17+** — plataforma
- **Adventure** — componentes de chat, action bar
- **MiniMessage 4.17.0** — formato de texto legible (`<red>`, `<gradient>`, `<click>`, etc.)
- **SQLite / MySQL** — persistencia de reports y warnings vía JDBC
- **Maven Shade** — empaquetado en `StaffControl.jar`

### Características
- `/vanish` — Modo vanish total (oculto de staff sin permiso `staffcontrol.staff`)
- `/freeze` — Congelar / descongelar jugadores (bloquea reingreso si está congelado)
- `/report` — Reportar jugadores con persistencia en base de datos
- `/warn` — Advertir jugadores con persistencia en base de datos
- `/staff` — Menú de staff: recarga de configuración (`reload`), chat privado de staff (`chat`)
- **Alertas** — Mención `@staff`, cambio de nombre, detección de x-ray
- **Staff chat** — Chat privado entre staff con formato customizable
- **Sistema de idiomas** — Archivos `messages-{locale}.yml` con MiniMessage

### Compilar

```bash
mvn clean package -pl staff-api,staff-modern-core -am
# → staff-modern-core/target/StaffControl.jar
```

Requiere JDK 17+ para compilar `staff-modern-core`.

### Instalación

1. Soltar `StaffControl.jar` en `plugins/`
2. Reiniciar el servidor
3. Configurar `plugins/StaffControl/config.yml`

---

## Staff+ (legacy, mantenimiento congelado)

Versión original para servidores 1.7 – 1.16. Solo recibe parches de seguridad.

### Compilar

```bash
bash install-dependencies.sh   # one-time
mvn clean package              # StaffPlusCore/target/Staff+.jar
```

Requiere JDK 8–11.

---

## API compartida (`staff-api`)

Las interfaces comunes están en `xyz.bt31.staffcontrol.api` (Java 8). Usada por ambas distribuciones.

### Módulos del reactor

| Módulo | JDK | Estado |
|---|---|---|
| `StaffPlusAPI` | 8 | Congelado |
| `staff-api` | 8 | Activo |
| `StaffPlusCore` | 8 | Congelado (comentado) |
| `v1_17_plus` | 16 | Congelado (comentado) |
| `staff-modern-core` | 17+ | Activo |

### Permisos

| Permiso | Descripción |
|---|---|
| `staffcontrol.*` | Todos los permisos (op por defecto) |
| `staffcontrol.vanish` | Usar `/vanish` |
| `staffcontrol.freeze` | Usar `/freeze` |
| `staffcontrol.report` | Usar `/report` (true por defecto) |
| `staffcontrol.warn` | Usar `/warn` |
| `staffcontrol.staff` | Usar `/staff` |
| `staffcontrol.reload` | Recargar configuración |
| `staffcontrol.alerts` | Recibir alertas |
| `staffcontrol.reports.receive` | Recibir notificaciones de reportes |
