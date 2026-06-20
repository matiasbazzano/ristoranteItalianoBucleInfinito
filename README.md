# El Bucle Infinito

Aplicación Spring Boot full-stack para la gestión de reservas del restaurante italiano *El Bucle Infinito*.  
Diseñada como **herramienta docente** para enseñar calidad de código con **SonarQube**: contiene malas prácticas intencionales (bugs, vulnerabilidades y code smells) que los alumnos deben descubrir analizando el proyecto.

---

## Requisitos previos

| Herramienta | Versión mínima | Notas |
|-------------|---------------|-------|
| Java JDK | 17 | Variable de entorno `JAVA_HOME` debe apuntar al JDK 17 |
| Maven | 3.6+ | O usar el wrapper `./mvnw` si está disponible |
| Docker + Docker Compose | Cualquier versión reciente | Solo necesario para el análisis con SonarQube |

> **Windows**: Si tienes varios JDKs instalados, fuerza el correcto antes de arrancar:
> ```powershell
> $env:JAVA_HOME = "C:\Program Files\Java\jdk-17"
> ```

---

## Ejecución local

```powershell
mvn spring-boot:run
```

La aplicación arranca en **http://localhost:8080**  
Base de datos H2 en memoria — no requiere configuración externa.

---

## Análisis con SonarQube

### 1. Arrancar SonarQube con Docker

```powershell
docker compose up -d
```

Espera unos segundos y entra en **http://localhost:9000**  
Credenciales por defecto: `admin` / `admin`

### 2. Crear el proyecto en SonarQube

1. Pulsa **Create Project → Manually**.
2. Project key: `bucleinfinito`
3. Genera un token de análisis y cópialo.

### 3. Ejecutar el análisis

```powershell
mvn sonar:sonar `
  -Dsonar.projectKey=bucleinfinito `
  -Dsonar.host.url=http://localhost:9000 `
  -Dsonar.login=<TOKEN>
```

Issues esperados: ~10 Bugs · ~8 Vulnerabilidades · ~20 Code Smells · ~3 Tests

---

## Stack tecnológico

- **Spring Boot 3.3.x** — framework principal
- **Thymeleaf** — plantillas HTML server-side
- **Spring Data JPA + H2** — persistencia en memoria
- **spring-boot-starter-validation** — validación de formularios
- **JUnit 5 + MockMvc** — tests

---

## Estructura del proyecto

```
src/main/java/com/example/bucleinfinito/
├── model/          BaseEntity, NamedEntity, Person
├── cliente/        Entidades, Repositorios, Controladores, Formatters, Validators
├── system/         WelcomeController, CrashController, WebMvcConfig
└── util/           RestauranteUtils
```

---

## Aviso importante

> Este proyecto contiene **malas prácticas de código deliberadas** con fines educativos.  
> No usar como base para aplicaciones en producción.

---

## Licencia

[MIT](LICENSE)
