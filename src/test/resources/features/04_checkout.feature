# language: es
@HU05-Checkout
Característica: Validación en el proceso de checkout y confirmación de compra
  Como usuario de La Botica del Alquimista
  Quiero que el sistema valide mis datos de envío
  Para evitar realizar pedidos con información incompleta o errónea

  Antecedentes:
    Dado que el usuario inicia sesión exitosamente en "https://boticaalquimista.onrender.com/index.html" con "alquimista" y "pocion123"
    Y tiene agregado "1" unidad del producto "Amuleto de Protección" en el carrito
    Y se encuentra en la pantalla del carrito de compras

  @Issue-11 @Negativo
  Escenario: Intentar confirmar pedido sin diligenciar campos obligatorios
    Dado que avanza hacia la pantalla de pago y envío
    Cuando intenta confirmar el pedido sin ingresar sus datos
    Entonces debería observar la alerta de checkout "Revisa los campos marcados"
    Y el campo de nombre debería señalar el error "Este campo es obligatorio"
