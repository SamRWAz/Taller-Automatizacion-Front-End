# Taller de Automatización Front-End con Serenity BDD y Screenplay

Repositorio de pruebas automatizadas de interfaz de usuario (Front-End) utilizando el framework **Serenity BDD**, **JUnit 5**, **Cucumber** y el patrón de diseño **Screenplay** sobre la aplicación web **La Botica del Alquimista**.

---

## Resumen del Proyecto

Este proyecto implementa la automatización de **11 escenarios de prueba** (5 positivos y 6 negativos) en formato Gherkin en español, cubriendo los módulos críticos de la plataforma:
- **Autenticación (Login):** Validaciones con credenciales válidas e inválidas.
- **Catálogo de Productos:** Búsqueda predictiva y filtrado dinámico por categorías.
- **Carrito de Compras y Stock:** Adición de productos y control de límites máximos por artículo.
- **Cupones de Descuento:** Aplicación de cupones válidos y validación de cupones inexistentes.
- **Checkout:** Validación de campos obligatorios para envíos y pedidos.

Cada escenario cuenta con capturas de pantalla automáticas paso a paso generadas por Serenity BDD como **Documentación Viva**.

---

## Matriz de Trazabilidad y GitHub Issues

Cada escenario está vinculado a un GitHub Issue y clasificado por Historia de Usuario (`@HUXX`) y tipo de prueba:

| Issue | Requisito / Historia de Usuario | Tipo | Escenario | Archivo .feature |
| :---: | :---: | :---: | :--- | :--- |
| **`@Issue-1`** | `@HU01-Autenticacion` | **Positivo** | Inicio de sesión exitoso con credenciales válidas (`alquimista`/`pocion123`) | `01_autenticacion.feature` |
| **`@Issue-2`** | `@HU01-Autenticacion` | **Negativo** | Inicio de sesión fallido con contraseña incorrecta | `01_autenticacion.feature` |
| **`@Issue-3`** | `@HU01-Autenticacion` | **Negativo** | Inicio de sesión fallido con usuario no registrado | `01_autenticacion.feature` |
| **`@Issue-4`** | `@HU02-Catalogo` | **Positivo** | Filtrar productos por la categoría Pociones | `02_catalogo.feature` |
| **`@Issue-5`** | `@HU02-Catalogo` | **Positivo** | Buscar un producto existente por su nombre ("Vitalidad") | `02_catalogo.feature` |
| **`@Issue-6`** | `@HU02-Catalogo` | **Negativo** | Buscar un producto inexistente sin coincidencias | `02_catalogo.feature` |
| **`@Issue-7`** | `@HU03-Carrito` | **Positivo** | Agregar un producto al carrito de compras con éxito | `03_carrito_y_cupones.feature` |
| **`@Issue-8`** | `@HU03-Carrito` | **Negativo** | Validación de cantidad máxima permitida por producto (> 5 unidades) | `03_carrito_y_cupones.feature` |
| **`@Issue-9`** | `@HU04-Cupones` | **Positivo** | Aplicar cupón de descuento válido en el carrito (`LUNA10`) | `03_carrito_y_cupones.feature` |
| **`@Issue-10`** | `@HU04-Cupones` | **Negativo** | Intentar aplicar un cupón de descuento inexistente (`CUPONFALSO99`) | `03_carrito_y_cupones.feature` |
| **`@Issue-11`** | `@HU05-Checkout` | **Negativo** | Intentar confirmar pedido sin diligenciar campos obligatorios | `04_checkout.feature` |

---

## Arquitectura del Proyecto (Patrón Screenplay)

El proyecto sigue rigurosamente el patrón de diseño Screenplay para garantizar alta cohesión, bajo acoplamiento y legibilidad en lenguaje de negocio:

```text
src/
├── main/java/Atm/
│   ├── ui/                    # Targets e Interfaces de Usuario (desacopladas)
│   │   ├── LoginUI.java
│   │   ├── CatalogoUI.java
│   │   ├── CarritoUI.java
│   │   └── CheckoutUI.java
│   ├── task/                  # Tareas de negocio (Tasks / Performable)
│   │   ├── IniciarSesion.java
│   │   ├── BuscarProducto.java
│   │   ├── FiltrarCatalogo.java
│   │   ├── AgregarAlCarrito.java
│   │   ├── IrAlCarrito.java
│   │   ├── AplicarCupon.java
│   │   ├── IrACheckout.java
│   │   └── ConfirmarPedido.java
│   └── Question/              # Preguntas y Aserciones (Questions)
│       ├── ElTexto.java
│       ├── ElEstado.java
│       └── LaCantidadDeElementos.java
└── test/
    ├── java/
    │   ├── runner/            # Test Suite Runner de Cucumber y Serenity
    │   │   └── Runner.java
    │   └── stepdefinitions/   # Definiciones de pasos Gherkin
    │       └── MyStepdefs.java
    └── resources/
        ├── features/          # Especificaciones en Gherkin (.feature)
        │   ├── 01_autenticacion.feature
        │   ├── 02_catalogo.feature
        │   ├── 03_carrito_y_cupones.feature
        │   └── 04_checkout.feature
        └── serenity.conf      # Configuración de Serenity y capacidades WebDriver
```

---

## Stack Tecnológico

- **Lenguaje:** Java 17
- **Gestor de Dependencias y Build:** Gradle 8.x / 9.x
- **Framework BDD y Reportes:** Serenity BDD 4.2.32
- **Motor BDD:** Cucumber JVM
- **Ejecución y Aserciones:** JUnit 5, Hamcrest, AssertJ
- **Driver:** Selenium WebDriver (Google Chrome Headless)

---

## Ejecución Local

### Ejecutar toda la suite y generar reporte de Serenity
```bash
./gradlew clean test aggregate
```
*(En Windows PowerShell o CMD usar `.\gradlew.bat clean test aggregate`)*

### Ejecutar por Tag específico
```bash
# Ejecutar un Issue específico (ej. Issue 1):
.\gradlew.bat test -Dcucumber.filter.tags="@Issue-1" aggregate

# Ejecutar únicamente escenarios Positivos:
.\gradlew.bat test -Dcucumber.filter.tags="@Positivo" aggregate

# Ejecutar únicamente escenarios Negativos:
.\gradlew.bat test -Dcucumber.filter.tags="@Negativo" aggregate
```

---

## Reporte de Documentación Viva

Al finalizar la ejecución, Serenity consolida la Documentación Viva en formato HTML interactivo con capturas de pantalla de cada acción:
- **Ruta del reporte:** `target/site/serenity/index.html`

Para abrir el reporte en Windows:
```powershell
Start-Process target/site/serenity/index.html
```

---

## Integración Continua (GitHub Actions)

El archivo `.github/workflows/ci.yml` ejecuta automáticamente las pruebas en cada `push` o `pull_request` a las ramas principales (`main`/`master`):
1. **Evaluación Individual por Prueba (Build Matrix):** Utiliza una matriz de trabajos en GitHub Actions con `fail-fast: false`, ejecutando cada escenario (`@Issue-1` a `@Issue-11`) en su propio contenedor aislado. Esto permite evaluar el estado independiente de cada prueba directamente en la interfaz de GitHub.
2. **Entorno de Ejecución:** Configurado con Java 17, caché de Gradle y Google Chrome Headless en Ubuntu.
3. **Manejo de Cold Start:** Realiza sondeo previo al backend en Render antes de lanzar las pruebas.
4. **Artefactos Serenity por Prueba:** Publica el reporte de Documentación Viva interactivo generado para cada Issue individualmente (`serenity-report-Issue-X`).
