# language: es
@HU02-Catalogo
Característica: Búsqueda y filtrado de productos en el catálogo
  Como usuario autenticado en la plataforma
  Quiero buscar y filtrar los artículos disponibles
  Para encontrar rápidamente las pociones o amuletos que necesito

  Antecedentes:
    Dado que el usuario inicia sesión exitosamente en "https://boticaalquimista.onrender.com/index.html" con "alquimista" y "pocion123"

  @Issue-4 @Positivo
  Escenario: Filtrar productos por la categoría Pociones
    Cuando filtra el catálogo por la categoría "Pociones"
    Entonces debería visualizar productos pertenecientes a la categoría seleccionada

  @Issue-5 @Positivo
  Escenario: Buscar un producto existente por su nombre
    Cuando busca en el catálogo el término "Vitalidad"
    Entonces debería visualizar el producto "Elixir de Vitalidad" en los resultados

  @Issue-6 @Negativo
  Escenario: Buscar un producto inexistente sin coincidencias
    Cuando busca en el catálogo el término "PociónTeletransportacionInexistente"
    Entonces debería observar el aviso de búsqueda "No se encontraron productos."
