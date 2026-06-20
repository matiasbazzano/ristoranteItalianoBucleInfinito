# Instrucciones para GitHub Copilot — El Bucle Infinito

## Propósito del proyecto

Aplicación Spring Boot full-stack inspirada en `spring-petclinic`, diseñada como **herramienta docente** para enseñar calidad de código con SonarQube. Contiene **malas prácticas deliberadas** distribuidas por el código para que los alumnos las descubran analizando con SonarQube. Los comentarios en el código (`// BAD PRACTICE [SXX]`) identifican cada regla.

> **REGLA CRÍTICA**: No corregir, refactorizar ni sugerir mejoras sobre el código existente salvo que el usuario lo pida explícitamente. Las malas prácticas son intencionales y deben permanecer tal cual.

---

## Stack tecnológico

| Elemento | Valor |
|----------|-------|
| Framework | Spring Boot 3.3.x |
| Java | 17 (requiere `JAVA_HOME` apuntando a JDK17) |
| Build | Maven |
| Vista | Thymeleaf (full-stack) |
| Persistencia | Spring Data JPA + H2 en memoria |
| Validación | spring-boot-starter-validation |
| Tests | JUnit 5 + MockMvc |

### Cómo ejecutar (Windows)

```powershell
$env:JAVA_HOME = "C:\Program Files\Java\JDK17"
mvn spring-boot:run
```

La app arranca en http://localhost:8080

> **Nota**: El sistema tiene múltiples JDKs (6, 7, 8, 11, 17). Maven usa el de JAVA_HOME. Si no se fuerza a JDK17 se produce el error `release version 17 not supported`.

---

## Dominio: Restaurante italiano "El Bucle Infinito"

```
Cliente     1 ──── * Reserva  1 ──── * Visita
Reserva     * ──── 1 Mesa
Reserva     * ──── 1 TipoMesa
Reserva     * ──── 1 ComidaEspecial
```

### Entidades

| Entidad | Campos clave |
|---------|-------------|
| `Cliente` | nombre, apellido, teléfono, dirección, ciudad, vip (boolean) |
| `Reserva` | dia (LocalDate), hora (LocalTime), cantidadPersonas, estado, mesa, tipoMesa, comidaEspecial |
| `Visita` | fechaVisita (LocalDate), notas |
| `Mesa` | numero, capacidad, activa |
| `TipoMesa` | nombre — valores: INTERIOR / EXTERIOR / VIP / PRIVADO |
| `ComidaEspecial` | nombre — valores: ESTÁNDAR / VEGETARIANA / SIN GLUTEN |

### Jerarquía de clases base

```
BaseEntity          ← id, equals() sin hashCode() [S1206 intencional]
  └── NamedEntity   ← nombre, List raw type [S3740 intencional], cast redundante [S1905 intencional]
        └── TipoMesa, ComidaEspecial
  └── Person        ← campos public [S1104 intencional]
        └── Cliente
```

---

## Estructura de paquetes

```
com.example.bucleinfinito
  ├── model/        BaseEntity, NamedEntity, Person
  ├── cliente/      Entidades, Repositorios, Controladores, Formatters, Validators
  ├── system/       WelcomeController, CrashController, WebMvcConfig
  └── util/         RestauranteUtils
```

---

## Malas prácticas intencionales — NO TOCAR

### Bugs

| Regla | Archivo | Descripción |
|-------|---------|-------------|
| S1206 | `BaseEntity` | `equals()` sin `hashCode()` |
| S2384 | `Cliente` | `getReservas()` devuelve la lista interna mutable |
| S3655 | `ClienteController` | `Optional.get()` sin `isPresent()` |
| S1132 | `Reserva` | `==` para comparar String de estado |
| S112  | `ReservaController` | `throws Exception` genérico |
| S1166 | `ReservaController` | `catch(Exception e) {}` vacío |
| S1481 | `Visita` | Variable local `temp` declarada y nunca usada |
| S1854 | `ClienteController` | Dead store — `x` y `temp` asignados y no leídos |
| S2259 | `MesaController` | `orElse(null)` sobre stream de mesas activas y dereferencía directa sin comprobar null |
| S2093 | `RestauranteUtils` | `StringWriter` abierto en `try` normal en lugar de `try-with-resources` |

### Vulnerabilidades

| Regla | Archivo | Descripción |
|-------|---------|-------------|
| S2068 | `application.properties` + `WelcomeController` | Password hardcodeado |
| S2076 | `ClienteRepository` | Native query con concatenación (SQL injection) |
| S5145 | `WelcomeController` | Log injection sin sanitizar |
| S2245 | `RestauranteUtils` | `new Random()` en vez de `SecureRandom` |
| S1104 | `Person` | Campos `public` sin encapsulación |
| S4790 | `RestauranteUtils` | Algoritmo de hash débil (`MD5`) en `generarHashCliente()` |
| S5542 | `WelcomeController` | Cifrado AES en modo ECB — no proporciona seguridad semántica |
| S5131 | `detalle.html` | `th:utext` renderiza las notas de visita sin escapar HTML (XSS) |

### Code Smells

| Regla | Archivo | Descripción |
|-------|---------|-------------|
| S1905 | `NamedEntity` | Cast redundante `(String)(Object)` |
| S3740 | `NamedEntity` | `List` sin genérico (raw type) |
| S1068 | `Mesa` | Campo `seccion` nunca leído |
| S1068 | `Visita` | Campo `codigoInterno` nunca leído |
| S1135 | `Reserva` | TODO sin resolver |
| S1144 | `Cliente` | Método privado `calcularPuntos()` dead code |
| S1192 | `Cliente` | Literal duplicado en 3 métodos |
| S117  | `ClienteController` | Variables `x`, `temp` sin nombre significativo |
| S138  | `ClienteController` | Método `listarClientes()` demasiado largo |
| S3776 | `ClienteController` | Complejidad ciclomática elevada |
| S3776 | `ReservaController` | Método `asignarMesas()` con complejidad cognitiva >20 (anidamiento profundo, requiere refactorización) |
| S3740 | `ClienteController` | `Map` raw type en `initCrearCliente` |
| S2301 | `ClienteController` | Argumento booleano selector en método público |
| S109  | `VisitaController` | Magic numbers `5`, `10`, `23` |
| S1118 | `RestauranteUtils` | Clase utilitaria con constructor `public` |
| S107  | `RestauranteUtils` | Método con 8 parámetros |
| S1192 | `RestauranteUtils` | Literal `"CONFIRMADA"` repetido 3 veces sin constante |
| S109  | `RestauranteUtils` | Números mágicos `200` y `4` usados varias veces sin constante |
| S125  | `Cliente`, `Reserva`, `ClienteController`, `RestauranteUtils`, `MesaController` | Código comentado (métodos y bloques lógicos que debieron eliminarse) |
| S105  | `Visita` | Método `getResumen()` indentado con tabuladores en vez de espacios |

### Tests

| Regla | Archivo | Descripción |
|-------|---------|-------------|
| S2699 | `ClienteControllerTests` | Test sin aserciones |
| S2925 | `ClienteControllerTests` | `Thread.sleep()` en test |
| S1607 | `ClienteControllerTests` | `@Disabled` sin justificación |

---

## Decisiones técnicas importantes tomadas durante el desarrollo

### Formatters (binding de formularios)
- `TipoMesaFormatter` y `ComidaEspecialFormatter` buscan por **nombre** (no por id).
- `MesaFormatter` busca por **id**.
- Los tres están registrados en `system/WebMvcConfig.java`.
- En los `<select>` de Thymeleaf: `tipoMesa` y `comidaEspecial` usan `th:value="${x.nombre}"`, `mesa` usa `th:value="${x.id}"`.

### Campos S1068 y Hibernate
- `Mesa.seccion` y `Visita.codigoInterno` son las malas prácticas S1068 (campos nunca leídos).
- Deben existir en `schema.sql` (`mesas.seccion`, `visitas.codigo_interno`) o Hibernate falla al arrancar.

### NamedEntity.historial y Hibernate
- `private List historial` (raw type S3740) lleva `@Transient` para que Hibernate no intente mapearlo.
- Sin `@Transient` la app no arranca (`Basic collection has element type 'E'`).

### Encoding
- `application.properties` incluye `spring.sql.init.encoding=UTF-8` para que los scripts SQL con tildes se lean correctamente en Windows.

### Lógica de asignación de mesas (`asignarMesas` en `ReservaController`)
- Al crear una reserva, si la mesa seleccionada no tiene capacidad suficiente para todas las personas, el método busca mesas adicionales activas y genera una `Reserva` extra por cada mesa adicional necesaria.
- Como último recurso usa mesas inactivas si las activas no alcanzan.
- Si el usuario no seleccionó mesa, asigna automáticamente la primera activa disponible.
- El método es intencionalmente complejo (S3776): 5 niveles de anidamiento, múltiples `for` anidados y operadores `&&` encadenados. **No refactorizar.**

### Tabuladores en `Visita.getResumen()` (S105)
- El método `getResumen()` al final de `Visita.java` usa tabuladores en vez de espacios para la indentación, mientras el resto del archivo usa espacios.
- Si el IDE o un formateador los convierte a espacios, la mala práctica S105 desaparece — restaurar manualmente.

---

## Análisis con SonarQube

```powershell
# 1. Levantar SonarQube (o usar docker-compose.yml en la raíz del proyecto)
docker compose up -d

# 2. Entrar en http://localhost:9000, crear proyecto "bucleinfinito", generar token

# 3. Analizar
mvn sonar:sonar `
  -Dsonar.projectKey=bucleinfinito `
  -Dsonar.host.url=http://localhost:9000 `
  -Dsonar.login=<TOKEN>
```

Issues esperados: ~10 Bugs · ~8 Vulnerabilidades · ~20 Code Smells · ~3 Tests = **~41 total**

---

## Diseño visual

- **Colores**: primario `#7B1D2E` (burdeos), acento `#C9A84C` (dorado), fondo `#F5F0E8` (marfil)
- **Tipografía**: Playfair Display (títulos) + Lato (cuerpo) — Google Fonts CDN
- **Logo**: `src/main/resources/static/images/logo.png`
- **Ciudades del restaurante**: San Sebastián · Montevideo
- **Pills de estado**: CONFIRMADA=verde, PENDIENTE=amarillo, CANCELADA=rojo
