# language: es
@HU01-Autenticacion
Característica: Autenticación de usuarios en La Botica del Alquimista
  Como usuario de La Botica del Alquimista
  Quiero ingresar con mis credenciales
  Para acceder al catálogo de pociones, ingredientes y amuletos mágicos

  @Issue-1 @Positivo
  Escenario: Inicio de sesión exitoso con credenciales válidas
    Dado que el usuario abre la página de inicio de sesión "https://boticaalquimista.onrender.com/index.html"
    Cuando ingresa el usuario "alquimista" y la contraseña "pocion123"
    Entonces debería ingresar al sistema y ver el saludo "Hola, alquimista"

  @Issue-2 @Negativo
  Escenario: Inicio de sesión fallido con contraseña incorrecta
    Dado que el usuario abre la página de inicio de sesión "https://boticaalquimista.onrender.com/index.html"
    Cuando ingresa el usuario "alquimista" y la contraseña "claveErronea99"
    Entonces debería observar el mensaje de error "Usuario o contraseña incorrectos"

  @Issue-3 @Negativo
  Escenario: Inicio de sesión fallido con usuario no registrado
    Dado que el usuario abre la página de inicio de sesión "https://boticaalquimista.onrender.com/index.html"
    Cuando ingresa el usuario "hechiceroFalso" y la contraseña "pocion123"
    Entonces debería observar el mensaje de error "Usuario o contraseña incorrectos"
