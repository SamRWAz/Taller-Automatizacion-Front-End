# Guía Maestra de Sustentación Oral - Taller Serenity BDD & Screenplay

Este documento contiene la explicación exhaustiva, técnica y pedagógica de toda la automatización realizada en **La Botica del Alquimista**, diseñada para que domines cada línea de código y defiendas tu proyecto con total seguridad ante el docente.

---

## Índice Rápido
1. [Ficha Técnica del Proyecto](#1-ficha-técnica-del-proyecto)
2. [Diagnóstico Inicial vs. Código Final (Qué se mejoró)](#2-diagnóstico-inicial-vs-código-final)
3. [El Patrón Screenplay Explicado a Fondo](#3-el-patrón-screenplay-explicado-a-fondo)
4. [Flujo de Ejecución: Del .feature a la Pantalla](#4-flujo-de-ejecución-del-feature-a-la-pantalla)
5. [Matriz de Trazabilidad y los 11 Escenarios Gherkin](#5-matriz-de-trazabilidad-y-los-11-escenarios-gherkin)
6. [Manejo de Tiempos Asíncronos y Sesiones (El Secreto Técnico)](#6-manejo-de-tiempos-asíncronos-y-sesiones)
7. [Comandos de Ejecución y Documentación Viva](#7-comandos-de-ejecución-y-documentación-viva)
8. [Banco de Preguntas Típicas de la Sustentación con Respuestas Modelo](#8-banco-de-preguntas-típicas-de-la-sustentación)
9. [Cómo Generar Localizadores en Vivo (Si el Docente te pide un cambio)](#9-cómo-generar-localizadores-en-vivo)

---

## 1. Ficha Técnica del Proyecto

* **Aplicación bajo pruebas:** *La Botica del Alquimista* (`https://boticaalquimista.onrender.com/index.html`)
* **Credenciales válidas de práctica:** Usuario: `alquimista` | Clave: `pocion123`
* **Tecnologías:**
  * **Java 17** (LTS)
  * **Gradle 8.x / 9.x** (Automatizador de compilación y dependencias)
  * **Serenity BDD 4.2.32** (Framework principal y generador de Documentación Viva)
  * **Cucumber JVM** (BDD con sintaxis Gherkin en español)
  * **JUnit 5 / Vintage Engine** (Motor de ejecución de pruebas)
  * **Selenium WebDriver** (Controlador de navegadores, Google Chrome)
* **Repositorio Remoto:** `https://github.com/SamRWAz/Taller-Automatizacion-Front-End.git`
* **Pipeline CI:** GitHub Actions (`.github/workflows/ci.yml`)

---

## 2. Diagnóstico Inicial vs. Código Final

Si el profesor te pregunta: *"¿Qué hiciste tú o cómo mejoraste lo que tenías inicialmente?"*, debes responder con estos puntos técnicos:

| Elemento | Cómo estaba al inicio | Cómo quedó refactorizado | Justificación técnica |
| :--- | :--- | :--- | :--- |
| **Arquitectura UI** | Carpeta `Atm.PO` con nombres estilo Page Object | Paquete `Atm.ui` con clases `LoginUI`, `CatalogoUI`, `CarritoUI`, `CheckoutUI` | En Screenplay **no existen Page Objects**. Las páginas monolíticas violan el Principio de Responsabilidad Única. Se usan clases de Targets desacoplados. |
| **Responsabilidad de Tasks** | `Logueo.java` solo digitaba campos; el clic se hacía en el Step Definition | `IniciarSesion.java` encapsula la intención de negocio completa (esperar, digitar usuario, digitar clave y hacer clic) | Los Step Definitions deben limitarse a coordinar al actor con las tareas, nunca contener acciones de bajo nivel dispersas. |
| **Control del Navegador** | `@Managed WebDriver driver` inyectado manualmente en los Steps | Delegado al Cast de Serenity (`OnlineCast`) mediante `OnStage.theActorCalled(...)` | Con `OnlineCast`, Serenity administra automáticamente el ciclo de vida del navegador, evitando fugas de memoria y errores en ejecuciones paralelas. |
| **Aserciones** | Se validaba si el botón seguía visible en login fallido | Aserciones con `ElTexto` y `ElEstado` validando mensajes textuales reales del sistema | Validar que un botón siga visible es una aserción débil (falso positivo). Validar el texto `"Usuario o contraseña incorrectos"` garantiza que el feedback al usuario es el esperado. |
| **Sincronización** | Pruebas fallaban por leer elementos antes de que terminara la animación | Esperas explícitas con `WaitUntil.the(target, isVisible())` en tareas y preguntas | La aplicación web tiene loaders artificiales (`esperaAleatoria`). Sin sincronización explícita, WebDriver lee texto vacío (`""`). |
| **Aislamiento de Pruebas** | La sesión quedaba guardada en `localStorage`, redirigiendo de `index.html` a `catalogo.html` en el siguiente test | Método `navegarConLimpieza()` que borra `localStorage` antes de cada escenario | Previene la contaminación de estado (*state bleeding*) entre escenarios en aplicaciones SPA. |

---

## 3. El Patrón Screenplay Explicado a Fondo

Screenplay es un patrón de diseño centrado en el usuario y guiado por los principios **SOLID**. Divide la automatización en 5 roles fundamentales:

```text
               ┌─────────────┐
               │    ACTOR    │ (Sujeto que ejecuta la prueba, ej: "Alquimista")
               └──────┬──────┘
                      │ puede (can)
               ┌──────▼──────┐
               │  HABILIDAD  │ (BrowseTheWeb: capacidad de manejar el navegador)
               └──────┬──────┘
                      │ intenta (attemptsTo)
         ┌────────────┴────────────┐
         │                         │
  ┌──────▼──────┐           ┌──────▼──────┐
  │    TAREA    │           │  PREGUNTA   │ (Question: consulta el estado)
  │   (Task)    │           │ (seeThat)   │
  └──────┬──────┘           └─────────────┘
         │ realiza (performAs)
  ┌──────▼──────┐
  │ INTERACCIÓN │ (Click, Enter, WaitUntil, SelectFromOptions)
  └──────┬──────┘
         │ sobre
  ┌──────▼──────┐
  │   TARGET    │ (Localizador del elemento UI con nombre legible)
  └─────────────┘
```

### 1. El Actor (`Actor`)
Es quien interactúa con el sistema. En tu proyecto se llama `"Alquimista"`.
* En `MyStepdefs.java`:
  ```java
  OnStage.setTheStage(new OnlineCast());
  theActorCalled("Alquimista");
  ```

### 2. La Habilidad (`Ability`)
Son los recursos que el actor tiene a su disposición. La habilidad principal es `BrowseTheWeb`, que le otorga un navegador real gestionado por Serenity.

### 3. Las Tareas (`Tasks`)
Representan las **intenciones de negocio** de alto nivel (lo que el usuario quiere lograr, no cada clic individual).
* Ejemplos en tu proyecto:
  * `IniciarSesion.conCredenciales(usuario, clave)`
  * `BuscarProducto.conElNombre(termino)`
  * `FiltrarCatalogo.porCategoria(categoria)`
  * `AgregarAlCarrito.elProducto(producto, cantidad)`
  * `AplicarCupon.conCodigo(codigo)`
  * `ConfirmarPedido.sinDatos()`

* **Estructura interna de una Task:**
  Implementa `Task`, tiene un método factory estático (`instrumented`) para que Serenity registre las capturas en el reporte, y sobreescribe `performAs(actor)`.

### 4. Las Interacciones (`Interactions`)
Son las acciones atómicas directas sobre el navegador:
* `Click.on(target)`
* `Enter.theValue(texto).into(target)`
* `Clear.field(target)`
* `SelectFromOptions.byVisibleText(texto).from(target)`
* `WaitUntil.the(target, isVisible()).forNoMoreThan(X).seconds()`

### 5. Los Targets (`Target`)
En lugar de almacenar `WebElement` pesados o métodos en Page Objects, los localizadores se definen como constantes `Target`.
* **Ventaja:** Tienen una descripción en lenguaje humano que Serenity usa para titular cada paso en el reporte HTML.
* Ejemplo estándar:
  ```java
  public static final Target CAMPO_USUARIO = Target.the("campo de usuario").located(By.id("usuario"));
  ```
* **Target Dinámico / Parametrizado:**
  Permite reutilizar el mismo Target para múltiples productos usando `{0}`:
  ```java
  public static final Target BOTON_AGREGAR_PRODUCTO = Target.the("botón agregar al carrito del producto {0}")
          .locatedBy("//article[contains(.,'{0}')]//button[contains(@class,'btn-agregar')]");
  ```
  Al usarlo se invoca: `BOTON_AGREGAR_PRODUCTO.of("Poción de Sueño Profundo")`.

### 6. Las Preguntas (`Questions`)
Consultan el estado del navegador para permitir aserciones sin acoplar la prueba.
* `ElTexto.de(target)`: Extrae el texto visible de un elemento (esperando primero a que esté visible).
* `ElEstado.esVisible(target)`: Devuelve `true` si el elemento está visible.
* `LaCantidadDeElementos.visibles(target)`: Cuenta cuántos elementos coinciden.
* **Aserción con `seeThat`:**
  ```java
  theActorInTheSpotlight().should(
      seeThat("El mensaje de error coincide", ElTexto.de(LoginUI.MENSAJE_ERROR), containsString("Usuario o contraseña incorrectos"))
  );
  ```

---

## 4. Flujo de Ejecución: Del .feature a la Pantalla

Si el docente te dice: *"Muéstrame cómo se conecta esta línea del Gherkin con el navegador"*:

```text
1. Gherkin (.feature):
   Cuando ingresa el usuario "alquimista" y la contraseña "pocion123"
                           │
                           ▼ (Cucumber busca la expresión regular o @Cuando)
2. Step Definition (MyStepdefs.java):
   @Cuando("ingresa el usuario {string} y la contraseña {string}")
   public void ingresaElUsuarioYLaContrasena(String usuario, String clave) {
       theActorInTheSpotlight().attemptsTo(IniciarSesion.conCredenciales(usuario, clave));
   }
                           │
                           ▼ (El actor ejecuta performAs())
3. Task (IniciarSesion.java):
   actor.attemptsTo(
       WaitUntil.the(LoginUI.CAMPO_USUARIO, isVisible()).forNoMoreThan(10).seconds(),
       Enter.theValue(usuario).into(LoginUI.CAMPO_USUARIO),
       Enter.theValue(clave).into(LoginUI.CAMPO_CLAVE),
       Click.on(LoginUI.BOTON_INGRESAR)
   );
                           │
                           ▼ (Cada interacción resuelve el Target)
4. UI Target (LoginUI.java):
   Target.the("campo de usuario").located(By.id("usuario"))
                           │
                           ▼ (Serenity le pide al WebDriver la acción)
5. Selenium WebDriver:
   driver.findElement(By.id("usuario")).sendKeys("alquimista")
   (Serenity toma la captura de pantalla automáticamente para la Documentación Viva)
```

---

## 5. Matriz de Trazabilidad y los 11 Escenarios Gherkin

Se implementaron **11 escenarios** (5 positivos y 6 negativos), superando el requisito de 10:

| # | Tag Issue | Tag Historia | Tipo | Nombre del Escenario | Validación Clave |
| :-: | :---: | :---: | :---: | :--- | :--- |
| **1** | `@Issue-1` | `@HU01-Autenticacion` | **Positivo** | Inicio de sesión exitoso | Saludo en la barra contiene `"Hola, alquimista"` |
| **2** | `@Issue-2` | `@HU01-Autenticacion` | **Negativo** | Contraseña incorrecta | Mensaje de error `"Usuario o contraseña incorrectos"` |
| **3** | `@Issue-3` | `@HU01-Autenticacion` | **Negativo** | Usuario no registrado | Mensaje de error `"Usuario o contraseña incorrectos"` |
| **4** | `@Issue-4` | `@HU02-Catalogo` | **Positivo** | Filtrar por categoría Pociones | Cantidad de tarjetas visibles > 0 |
| **5** | `@Issue-5` | `@HU02-Catalogo` | **Positivo** | Buscar producto existente | Producto `"Elixir de Vitalidad"` es visible |
| **6** | `@Issue-6` | `@HU02-Catalogo` | **Negativo** | Buscar producto inexistente | Mensaje `"No se encontraron productos."` |
| **7** | `@Issue-7` | `@HU03-Carrito` | **Positivo** | Agregar producto al carrito | Contador en header muestra al menos `"2"` |
| **8** | `@Issue-8` | `@HU03-Carrito` | **Negativo** | Exceder cantidad máxima (> 5) | Alerta `"La cantidad debe estar entre 1 y 5"` |
| **9** | `@Issue-9` | `@HU04-Cupones` | **Positivo** | Aplicar cupón válido `LUNA10` | Mensaje `"Cupón LUNA10 aplicado correctamente"` |
| **10**| `@Issue-10`| `@HU04-Cupones` | **Negativo** | Aplicar cupón inexistente | Mensaje de error `"Cupón no válido"` |
| **11**| `@Issue-11`| `@HU05-Checkout` | **Negativo** | Confirmar pedido sin datos | Alerta `"Revisa los campos marcados"` y error en nombre |

---

## 6. Manejo de Tiempos Asíncronos y Sesiones

Este fue el mayor desafío técnico que resolviste en el proyecto. Debes explicarlo con claridad:

### A. El problema de la "espera simulada" de la aplicación
En el código JavaScript de la tienda (`comun.js`), el desarrollador incluyó:
```javascript
mostrarCargando('Verificando credenciales...');
await esperaAleatoria(300, 700);
ocultarCargando();
```
* **Por qué fallaba al inicio:** Selenium es extremadamente rápido; lee el elemento en milisegundos. Cuando intentaba validar el mensaje de error o el cupón, el elemento aún estaba oculto (`hidden = true`) o con texto vacío (`""`).
* **Cómo lo solucionaste:** Con esperas explícitas de Screenplay:
  ```java
  WaitUntil.the(LoginUI.MENSAJE_ERROR, isVisible()).forNoMoreThan(15).seconds()
  ```
  Y haciendo que la Question `ElTexto` llame a `target.resolveFor(actor).waitUntilVisible()` antes de obtener el valor.

### B. El problema del `localStorage` y la redirección automática
* En `index.html`:
  ```javascript
  if (usuarioActual()) {
    window.location.href = 'catalogo.html';
  }
  ```
* **Por qué fallaba:** Si el escenario anterior dejaba la sesión abierta en el navegador, al empezar el siguiente escenario en `index.html`, la página redirigía instantáneamente a `catalogo.html`. Por tanto, el formulario de login desaparecía y las pruebas fallaban con *timeout*.
* **Cómo lo solucionaste:** Con el método `navegarConLimpieza(url)` en `MyStepdefs.java`, que limpia `localStorage` y `sessionStorage` mediante JavaScript antes de ejecutar el escenario.

---

## 7. Comandos de Ejecución y Documentación Viva

### Comandos de Consola (Gradle Wrapper)

* **Ejecutar toda la suite y generar reporte:**
  ```powershell
  .\gradlew.bat clean test aggregate
  ```
* **Ejecutar un Issue específico (ejemplo Issue 4):**
  ```powershell
  .\gradlew.bat test -Dcucumber.filter.tags="@Issue-4" aggregate
  ```
* **Ejecutar únicamente escenarios negativos:**
  ```powershell
  .\gradlew.bat test -Dcucumber.filter.tags="@Negativo" aggregate
  ```
* **Ejecutar únicamente escenarios positivos:**
  ```powershell
  .\gradlew.bat test -Dcucumber.filter.tags="@Positivo" aggregate
  ```

### ¿Dónde está la Documentación Viva?
La ruta del reporte consolidado es:
`target/site/serenity/index.html`

Para abrirlo en Windows:
```powershell
Start-Process target/site/serenity/index.html
```

---

## 8. Banco de Preguntas Típicas de la Sustentación

### P1: ¿Por qué usaron Screenplay y no Page Object Model?
> **Respuesta:** *"Page Object Model tiende a crear clases gigantescas donde se mezclan localizadores, lógica de interacción y aserciones, violando el Principio de Responsabilidad Única. Screenplay aplica SOLID: desacopla los elementos en Targets (capa UI), las intenciones de usuario en Tasks reutilizables, y las verificaciones en Questions, haciendo el código inmensamente más mantenible, modular y legible como negocio."*

### P2: ¿Quién gestiona el WebDriver y cómo se evitan conflictos entre actores?
> **Respuesta:** *"Utilizamos el patrón Cast de Serenity mediante `OnStage.setTheStage(new OnlineCast())` en el hook `@Before`. Cada vez que llamamos a `theActorCalled("Alquimista")`, Serenity le asigna la habilidad `BrowseTheWeb` con una instancia limpia del WebDriver. No inyectamos `@Managed WebDriver` manualmente en los steps para evitar fugas de memoria y respetar el ciclo de vida del framework."*

### P3: ¿Cómo garantiza el proyecto que las pruebas no se afecten entre sí?
> **Respuesta:** *"La aplicación web almacena sesión y carrito en `localStorage`. Para evitar que un escenario herede datos del anterior (state bleeding), implementamos `navegarConLimpieza()`, que borra `localStorage` y `sessionStorage` antes de cada prueba. Además, en `serenity.conf` configuramos timeouts explícitos de 15 segundos y argumentos headless para entornos Linux."*

### P4: ¿Cómo se logra la trazabilidad con los GitHub Issues?
> **Respuesta:** *"En cada archivo `.feature`, decoramos los escenarios con tags normalizados: `@HUXX` para la Historia de Usuario, `@Positivo`/`@Negativo` para la cobertura, y `@Issue-X` para vincularlo directamente con el número de Issue creado en el repositorio de GitHub. De esta forma, el reporte de Serenity agrupa y traza las pruebas exactamente contra los requisitos del negocio."*

### P5: ¿Por qué el pipeline de GitHub Actions necesita el paso de 'curl'?
> **Respuesta:** *"Porque la aplicación está desplegada en el tier gratuito de Render, el cual hiberna tras 15 minutos de inactividad (Cold Start). Si las pruebas arrancan sin que el servidor esté activo, la primera petición puede tardar 45 segundos y causar timeouts. Con `curl --retry`, despertamos la máquina antes de iniciar Gradle y garantizamos que el servidor responda HTTP 200."*

---

## 9. Cómo Generar Localizadores en Vivo

Si durante la sustentación el docente te dice: *"Muéstrame cómo crearías un localizador manualmente para el botón 'Ingresar' o para el campo de búsqueda"*:

### 1. Por ID (La opción más rápida y limpia si el ID es estático)
```java
// Para: <input type="search" id="buscar">
Target.the("campo de búsqueda").located(By.id("buscar"));
```

### 2. Por atributo de prueba `data-testid` (Mejor práctica recomendada en QA)
```java
// Para: <p class="mensaje-error" data-testid="error-login">
Target.the("mensaje de error").located(By.cssSelector("[data-testid='error-login']"));
```

### 3. Por XPath basado en texto visible
```java
// Para: <button type="submit">Ingresar</button>
Target.the("botón ingresar").located(By.xpath("//button[contains(text(),'Ingresar')]"));
```

### 4. Por XPath relativo anidado (Contenedor + Elemento interno)
```java
// Para el botón de un producto específico dentro de su tarjeta:
Target.the("botón agregar").locatedBy("//article[contains(.,'{0}')]//button[contains(@class,'btn-agregar')]");
```

---
*¡Con esta guía tienes dominio total de la arquitectura, el código, el pipeline y los conceptos para sustentar con la máxima nota!*
