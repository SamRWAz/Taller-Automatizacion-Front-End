# language: es
@HU03-Carrito @HU04-Cupones
Característica: Gestión del carrito de compras y cupones de descuento
  Como comprador de La Botica del Alquimista
  Quiero gestionar mi carrito de compras y aplicar cupones
  Para adquirir mis productos con los beneficios correspondientes

  Antecedentes:
    Dado que el usuario inicia sesión exitosamente en "https://boticaalquimista.onrender.com/index.html" con "alquimista" y "pocion123"

  @Issue-7 @Positivo
  Escenario: Agregar un producto al carrito de compras con éxito
    Cuando agrega "2" unidades del producto "Poción de Sueño Profundo" al carrito
    Entonces el contador del carrito debería mostrar al menos "2" unidades
    Y debería ver la notificación "Se agregó Poción de Sueño Profundo al carrito"

  @Issue-8 @Negativo
  Escenario: Validación de cantidad máxima permitida por producto
    Cuando agrega "6" unidades del producto "Poción de Sueño Profundo" al carrito
    Entonces debería observar la alerta "La cantidad debe estar entre 1 y 5"

  @Issue-9 @Positivo
  Escenario: Aplicar cupón de descuento válido en el carrito
    Dado que tiene agregado "1" unidad del producto "Amuleto de Protección" en el carrito
    Y se encuentra en la pantalla del carrito de compras
    Cuando aplica el cupón de descuento "LUNA10"
    Entonces debería observar el mensaje de cupón "Cupón LUNA10 aplicado correctamente"
    Y el resumen debería reflejar el descuento aplicado

  @Issue-10 @Negativo
  Escenario: Intentar aplicar un cupón de descuento inexistente
    Dado que tiene agregado "1" unidad del producto "Amuleto de Protección" en el carrito
    Y se encuentra en la pantalla del carrito de compras
    Cuando aplica el cupón de descuento "CUPONFALSO99"
    Entonces debería observar el mensaje de cupón "Cupón no válido"
